package dealerstat.service;

import dealerstat.dto.AuthenticationRequestDto;
import dealerstat.entity.MyUser;
import dealerstat.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final MyUserService userService;


    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String email = requestDto.getEmail().toLowerCase(Locale.ROOT);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
            MyUser user = userService.findMyUserByEmail(email);

            if (user == null) {
                return ResponseEntity.status(404).body("User with email " + email + " not found");
            } else if (!user.isApproved()) {
                return ResponseEntity.status(403).body("Admin hasn't yet confirmed your request");
            } else {

                String token = jwtTokenProvider.createToken(email, user.getId(), user.getRoles());

                Map<Object, Object> response = new HashMap<>();
                response.put("username", email);
                response.put("token", token);

                return ResponseEntity.ok(response);
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(404).body("Invalid email or password");
        }
    }

}

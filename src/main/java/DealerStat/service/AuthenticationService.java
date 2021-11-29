package DealerStat.service;

import DealerStat.dto.AuthenticationRequestDto;
import DealerStat.entity.MyUser;
import DealerStat.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
            }

            String token = jwtTokenProvider.createToken(email, user.getId(), user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", email);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(404).body("Invalid email or password");
        }
    }

}

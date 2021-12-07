package dealerstat.service;

import dealerstat.dto.AuthenticationRequestDto;
import dealerstat.dto.MyUserDto;
import dealerstat.entity.MyUser;
import dealerstat.entity.Role;
import dealerstat.config.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final MyUserService myUserService;

    private final RedisService redisService;

    private final BCryptPasswordEncoder passwordEncoder;

    private final EmailSenderService emailSenderService;

    @Value("${spring.mail.username}")
    private String emailSender;

    @Value("${server.port}")
    private String port;

    public ResponseEntity<?> registerUser(@RequestBody @Valid MyUserDto myUserDto) {
        String email = (String) redisService.getToken(myUserDto.getEmail());
        MyUser myUser = myUserService.findMyUserByEmail(myUserDto.getEmail().toLowerCase(Locale.ROOT));

        if (myUser == null && email == null) {
            return createMyUserForRegistration(myUserDto, null, null, 0.0);
        } else if (email != null) {
            return ResponseEntity.status(404).body("Check your email you have already registered");
        } else if (myUser.getPassword() != null) {
            return ResponseEntity.status(404).body("User with email " + myUser.getEmail() + " already exist");
        } else {
            return createMyUserForRegistration(myUserDto, myUser.getId(), myUser.getCreatedAt(), myUser.getRating());
        }
    }

    private ResponseEntity<?> createMyUserForRegistration(MyUserDto myUserDto, Long id, Date createdAt,
                                                          Double rating) {
        MyUser myUser = new MyUser();

        if (id != null) {
            myUser.setId(id);
            myUser.setCreatedAt(createdAt);
        }

        myUser.setFirstName(myUserDto.getFirstName());
        myUser.setLastName(myUserDto.getLastName());
        myUser.setPassword(passwordEncoder.encode(myUserDto.getPassword()));
        myUser.setEmail(myUserDto.getEmail().toLowerCase(Locale.ROOT));
        myUser.setRoles(Collections.singletonList(Role.TRADER));
        myUser.setRating(rating);
        myUser.setApproved(false);

        String token = UUID.randomUUID().toString();

        messageSender(myUserDto.getEmail(), token, "confirm");
        redisService.putToken(token, myUser);
        redisService.putToken(myUserDto.getEmail(), "CHECK");

        return ResponseEntity.ok("To complete the registration, check your email, please.");
    }

    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequestDto requestDto) {
        try {
            String email = requestDto.getEmail().toLowerCase(Locale.ROOT);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
            MyUser user = myUserService.findMyUserByEmail(email);

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


    public ResponseEntity<?> confirmAccount(String token) {
        MyUser myUser = (MyUser) redisService.getToken(token);

        if (myUser != null) {
            myUserService.save(myUser);
            redisService.deleteToken(myUser.getEmail());
            redisService.deleteToken(token);
            return ResponseEntity.ok("Complete registration! Please, wait for admin confirmation");
        } else {
            return ResponseEntity.status(404).body("The token's lifetime has expired");
        }
    }

    public ResponseEntity<?> sendMessageForSetPassword(String email) {
        MyUser myUser = myUserService.findMyUserByEmail(email.toLowerCase(Locale.ROOT));

        if (myUser != null) {
            String token = UUID.randomUUID().toString();

            messageSender(email, token, "reset");

            redisService.putToken(token, email);

            return ResponseEntity.ok("Confirmation email has been sent to your email");
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    public ResponseEntity<?> setPassword(String password, String token) {
        if (password.length() >= 8) {
            String email = (String) redisService.getToken(token);
            if (email != null && myUserService.findMyUserByEmail(email) != null) {
                MyUser myUser = myUserService.findMyUserByEmail(email);
                myUser.setPassword(passwordEncoder.encode(password));
                myUserService.save(myUser);
                redisService.deleteToken(token);
                return ResponseEntity.ok("Your password has been successfully changed!");
            } else {
                return ResponseEntity.status(404).body("The token's lifetime has expired");
            }
        } else {
            return ResponseEntity.status(404).body("Password should be more than 8");
        }

    }

    public ResponseEntity<?> checkCode(String token) {
        String check = (String) redisService.getToken(token);

        if (check != null) {
            return ResponseEntity.ok("The token's lifetime has not expired");
        } else {
            return ResponseEntity.status(404).body("The token's lifetime has expired");
        }
    }


    private void messageSender(String email, String token, String url) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Please, confirm your email");
        mailMessage.setFrom(emailSender);
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/auth/" + url + "?token=" + token);

        emailSenderService.sendEmail(mailMessage);
    }


}

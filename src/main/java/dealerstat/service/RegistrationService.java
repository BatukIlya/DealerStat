package dealerstat.service;

import dealerstat.dto.MyUserDto;
import dealerstat.entity.MyUser;
import dealerstat.entity.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Locale;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationService {

    private final MyUserService myUserService;

    private final RedisService redisService;

    private final BCryptPasswordEncoder passwordEncoder;

    private final EmailSenderService emailSenderService;

    @Value("${spring.mail.username}")
    private String emailSender;

    @Value("${server.port}")
    private String port;

    public ResponseEntity registerUser(MyUserDto myUserDto) {
        String email = (String) redisService.getToken(myUserDto.getEmail());
        MyUser myUser = myUserService.findMyUserByEmail(myUserDto.getEmail());
        if (myUser == null & email == null) {
            MyUser myUser1 = new MyUser();
            myUser1.setFirstName(myUserDto.getFirstName());
            myUser1.setLastName(myUserDto.getLastName());
            myUser1.setPassword(passwordEncoder.encode(myUserDto.getPassword()));
            myUser1.setEmail(myUserDto.getEmail().toLowerCase(Locale.ROOT));
            myUser1.setRoles(Collections.singletonList(Role.TRADER));
            myUser1.setRating(0.0);

            String token = UUID.randomUUID().toString();

            messageSender(myUserDto.getEmail(), token, "confirm_account");

            redisService.putToken(token, myUser1);
            redisService.putToken(myUserDto.getEmail(), "CHECK");

            return ResponseEntity.ok("To complete the registration, check your email, please.");
        } else if (myUser != null) {
            return ResponseEntity.status(404).body("User with email " + myUser.getEmail() + " already exist");
        } else {
            return ResponseEntity.status(404).body("Check your email you have already registered");
        }
    }

    public ResponseEntity sendMessageForSetPassword(String email) {
        MyUser myUser = myUserService.findMyUserByEmail(email);
        if (myUser != null) {
            String token = UUID.randomUUID().toString();

            messageSender(email, token, "set_password");

            redisService.putToken(token, email);

            return ResponseEntity.ok("Confirmation email has been sent to your email");
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    public ResponseEntity setPassword(String password, String token) {
        String email = (String) redisService.getToken(token);
        if (email != null) {
            redisService.deleteToken(token);
            MyUser myUser = myUserService.findMyUserByEmail(email);
            myUser.setPassword(passwordEncoder.encode(password));
            myUserService.save(myUser);
            return ResponseEntity.ok("Your password has been successfully changed!");
        } else {
            return ResponseEntity.status(404).body("The token's lifetime has expired");
        }
    }


    public ResponseEntity confirmAccount(String token) {
        MyUser myUser = (MyUser) redisService.getToken(token);
        if (myUser != null) {
            redisService.deleteToken(token);
            myUserService.save(myUser);
            return ResponseEntity.ok("Complete registration! Please, wait for admin confirmation");
        } else {
            return ResponseEntity.status(404).body("The token's lifetime has expired");
        }
    }

    private void messageSender(String email, String token, String url){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Please, confirm your email");
        mailMessage.setFrom(emailSender);
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:" + port + "/" + url + "?token=" + token);

        emailSenderService.sendEmail(mailMessage);
    }

}

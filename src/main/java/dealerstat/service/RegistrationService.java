package dealerstat.service;

import dealerstat.dto.MyUserDto;
import dealerstat.entity.MyUser;
import dealerstat.entity.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    public MyUser registerUser(MyUserDto myUserDto) {
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

            messageSender(myUserDto.getEmail(), token);

            redisService.putToken(token, myUser1);
            redisService.putToken(myUserDto.getEmail(), "CHECK");

            return null;
        } else if (myUser != null) {
            log.info("User with email " + myUser.getEmail() + " already exist");
            return null;
        } else {
            log.info("Check your email you have already registered");
            return null;
        }
    }

    public String sendMessageForSetPassword(String email) {
        MyUser myUser = myUserService.findMyUserByEmail(email);
        if (myUser != null) {
            String token = UUID.randomUUID().toString();

            messageSender(email, token);

            redisService.putToken(token, email);

            return "Confirmation email has been sent to your email";
        } else {
            return "There is no such user";
        }
    }

    public String setPassword(String password, String token) {
        String email = (String) redisService.getToken(token);
        if (email != null) {
            redisService.deleteToken(token);
            MyUser myUser = myUserService.findMyUserByEmail(email);
            myUser.setPassword(passwordEncoder.encode(password));
            myUserService.save(myUser);
            return "Your password has been successfully changed!";
        } else {
            return "The token's lifetime has expired";
        }
    }


    public String confirmAccount(String token) {
        MyUser myUser = (MyUser) redisService.getToken(token);
        if (myUser != null) {
            redisService.deleteToken(token);
            myUserService.save(myUser);
            return "Complete registration! Please, wait for admin confirmation";
        } else {
            return "The token's lifetime has expired";
        }
    }

    private void messageSender(String email, String token){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Please, confirm your email");
        mailMessage.setFrom(emailSender);
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:" + port + "/confirm-account?token=" + token);

        emailSenderService.sendEmail(mailMessage);
    }

}

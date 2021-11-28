package DealerStat.service;

import DealerStat.dto.MyUserDto;
import DealerStat.entity.MyUser;
import DealerStat.entity.Role;
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
        MyUser myUser = myUserService.findMyUserByEmail(myUserDto.getEmail());
        if (myUser != null) {
            if (myUser.isApprovedEmail()) {
                log.info("User with email " + myUser.getEmail() + " already exist");
                return null;
            } else {
                messageSender(myUser.getEmail());
                log.info("Repeated confirmation email has been sent to your email");
                return null;
            }
        } else {
            MyUser myUser1 = new MyUser();
            myUser1.setFirstName(myUserDto.getFirstName());
            myUser1.setLastName(myUserDto.getLastName());
            myUser1.setPassword(passwordEncoder.encode(myUserDto.getPassword()));
            myUser1.setEmail(myUserDto.getEmail().toLowerCase(Locale.ROOT));
            myUser1.setRoles(Collections.singletonList(Role.USER));

            messageSender(myUserDto.getEmail());

            return myUserService.save(myUser1);
        }

    }

    private void messageSender(String email) {
        String token = UUID.randomUUID().toString();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete registration!!!");
        mailMessage.setFrom(emailSender);
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:" + port + "/confirm-account?token=" + token);

        emailSenderService.sendEmail(mailMessage);

        redisService.putToken(token, email);
    }

    public String confirmAccount(String token) {
        String email = redisService.getToken(token);
        if (email != null) {
            MyUser myUser = myUserService.findMyUserByEmail(email);
            myUser.setApprovedEmail(true);
            myUserService.save(myUser);
            return "Complete registration! Please, log in.";
        } else {
            return "The token's lifetime has expired";
        }
    }
}

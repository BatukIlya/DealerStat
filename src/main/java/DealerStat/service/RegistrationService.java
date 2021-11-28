package DealerStat.service;

import DealerStat.dto.MyUserDto;
import DealerStat.entity.MyUser;
import DealerStat.entity.Role;
import DealerStat.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${server.port}")
    private String port;

    public MyUser registerUser(MyUserDto myUserDto) {
        MyUser myUser = myUserService.findMyUserByEmail(myUserDto.getEmail());
        if(myUser != null){
            if(myUser.isApprovedEmail()){
                log.info("User with email " + myUser.getEmail() + " already exist");
                return null;
            }else{
                messageSender(myUser.getEmail());
                log.info("Repeated confirmation email has been sent to your email");
                return null;
            }
        }else{
            MyUser myUser1 = new MyUser();
            myUser1.setFirstName(myUserDto.getFirstName());
            myUser1.setLastName(myUserDto.getLastName());
            myUser1.setPassword(passwordEncoder.encode(myUserDto.getPassword()));
            myUser1.setEmail(myUserDto.getEmail().toLowerCase(Locale.ROOT));
            myUser1.setRoles(Collections.singletonList(Role.USER));

            messageSender(myUserDto.getEmail());

            //отправка сообщения на почту о подтверждении
            return myUserService.save(myUser1);
        }

    }

    private void messageSender(String email){
        String token = UUID.randomUUID().toString();
        System.out.println("To confirm your account, please click here : "
                +"http://localhost:" + port + "/confirm-account?token=" + token);
        redisService.putToken(token, email);
    }

    public MyUser confirmAccount(String token){
        String email = redisService.getToken(token);
        if(email != null){
            MyUser myUser = myUserService.findMyUserByEmail(email);
            myUser.setApprovedEmail(true);
            return myUserService.save(myUser);
        }else{
            log.info("The token's lifetime has expired");
            return null;
        }
    }
}

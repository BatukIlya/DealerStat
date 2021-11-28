package DealerStat.сontroller;

import DealerStat.dto.MyUserDto;
import DealerStat.entity.MyUser;
import DealerStat.repository.MyUserRepository;
import DealerStat.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;


    @PostMapping("/register")
    public MyUser registration(@RequestBody MyUserDto myUserDto){
        return registrationService.registerUser(myUserDto);
    }

    @GetMapping("/confirm-account")
    public MyUser confirmAccount(@RequestParam("token") String token){
        return registrationService.confirmAccount(token);
    }
}

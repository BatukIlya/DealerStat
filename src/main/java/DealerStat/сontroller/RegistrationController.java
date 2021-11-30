package DealerStat.сontroller;

import DealerStat.dto.MyUserDto;
import DealerStat.entity.MyUser;
import DealerStat.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;


    @PostMapping("/register")
    public MyUser registration(@RequestBody MyUserDto myUserDto) {
        return registrationService.registerUser(myUserDto);
    }

//    @PreAuthorize("permitAll()")
    @PostMapping("/set_password")
    public String sendMessageForSetPassword(String email) {
        return registrationService.sendMessageForSetPassword(email);
    }

    @PutMapping("/confirm-account")
    public String setPassword(@RequestParam("token") String token, @RequestBody String password) {
        return registrationService.setPassword(password, token);
    }

    @GetMapping("/confirm-account")
    public String confirmAccount(@RequestParam("token") String token) {
        return registrationService.confirmAccount(token);
    }
}

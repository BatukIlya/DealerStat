package dealerstat.сontroller;

import dealerstat.dto.MyUserDto;
import dealerstat.entity.MyUser;
import dealerstat.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;


    @PostMapping("/register")
    public ResponseEntity registration(@RequestBody MyUserDto myUserDto) {
        return registrationService.registerUser(myUserDto);
    }

    @PostMapping("/set_password")
    public ResponseEntity sendMessageForSetPassword(String email) {
        return registrationService.sendMessageForSetPassword(email);
    }

    @PutMapping("/set_password")
    public ResponseEntity setPassword(@RequestParam("token") String token, @RequestBody String password) {
        return registrationService.setPassword(password, token);
    }

    @GetMapping("/confirm_account")
    public ResponseEntity confirmAccount(@RequestParam("token") String token) {
        return registrationService.confirmAccount(token);
    }
}

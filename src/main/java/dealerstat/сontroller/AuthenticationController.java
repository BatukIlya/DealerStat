package dealerstat.сontroller;

import dealerstat.dto.AuthenticationRequestDto;
import dealerstat.dto.MyUserDto;
import dealerstat.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequestDto requestDto) {
        return authenticationService.login(requestDto);
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<?> sendMessageForSetPassword(@RequestBody String email) {
        return authenticationService.sendMessageForSetPassword(email);
    }

    @GetMapping("/check_code")
    public ResponseEntity<?> checkCode(@RequestParam("token") String token) {
        return authenticationService.checkCode(token);
    }

    @PostMapping("/reset")
    public ResponseEntity<?> setPassword(@RequestParam("token") String token, @RequestBody String password) {
        return authenticationService.setPassword(password, token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registration(@RequestBody @Valid MyUserDto myUserDto) {
        return authenticationService.registerUser(myUserDto);
    }


    @GetMapping("/confirm")
    public ResponseEntity<?> confirmAccount(@RequestParam("token") String token) {
        return authenticationService.confirmAccount(token);
    }

}
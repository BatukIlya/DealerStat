package DealerStat.сontroller;

import DealerStat.dto.MyUserDto;
import DealerStat.entity.MyUser;
import DealerStat.entity.Role;
import DealerStat.repository.MyUserRepository;
import DealerStat.service.MyUserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;


@RestController("/admin")
@Api
@RequiredArgsConstructor
public class MyUserController {

    private final MyUserService myUserService;

    private final MyUserRepository myUserRepository;


    @GetMapping("/registration")
    public String registration()
    {
        return "registration ";
    }


    @PostMapping("/registration")
    public MyUser createUser(@RequestBody MyUserDto myUserDto) {
        return myUserService.createUser(myUserDto);
    }

    @PutMapping("/users/tickets/{userId}/")
    public MyUser approveUser(@PathVariable Long userId){
        return myUserService.approveUser(userId);
    }
}

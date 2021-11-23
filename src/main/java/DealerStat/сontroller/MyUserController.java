package DealerStat.сontroller;

import DealerStat.dto.MyUserDto;
import DealerStat.entity.MyUser;
import DealerStat.repository.MyUserRepository;
import DealerStat.service.MyUserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



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

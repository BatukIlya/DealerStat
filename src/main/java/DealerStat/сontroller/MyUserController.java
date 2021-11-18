package DealerStat.сontroller;

import DealerStat.dto.MyUserDto;
import DealerStat.entity.MyUser;
import DealerStat.service.MyUserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController("/admin")
@Api
@RequiredArgsConstructor
public class MyUserController {

    private final MyUserService service;


    @GetMapping("/home")
    public String home() {
        return "Hello World";
    }

    @PostMapping("/create")
    public MyUser createPeople(@RequestBody MyUserDto myUserDto) {
        return service.createPeople(myUserDto);
    }
}

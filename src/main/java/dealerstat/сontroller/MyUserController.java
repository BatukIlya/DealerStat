package dealerstat.сontroller;

import dealerstat.dto.SearchCriteria;
import dealerstat.entity.MyUser;
import dealerstat.service.MyUserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Api
@RequiredArgsConstructor
public class MyUserController {


    private final MyUserService myUserService;


    @GetMapping("/users/{id}")
    public MyUser showTrader(@PathVariable Long id) {
        return myUserService.findMyUserById(id);
    }

    @PostMapping("/users/")
    public ResponseEntity<?> showAllTraders(@RequestBody(required = false) SearchCriteria searchCriteria) {
        return myUserService.showAllTraders(searchCriteria);
    }


}





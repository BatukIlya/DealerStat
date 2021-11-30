package dealerstat.сontroller;

import dealerstat.entity.MyUser;
import dealerstat.service.MyUserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api
@RequiredArgsConstructor
public class MyUserController {


    private final MyUserService myUserService;

    @GetMapping("/users")
    public ResponseEntity<?> showAllTraders(){
        return myUserService.showAllTraders();
    }

    @GetMapping("/users/{id}")
    public MyUser showTrader(@PathVariable Long id){
        return myUserService.findMyUserById(id);
    }

    @GetMapping("/users/filter_by_desc_rating")
    public ResponseEntity<?> showAllTradersByDescRating() {
        return myUserService.showAllTradersByDescRating();
    }

    @GetMapping("/users/filter_by_asc_rating")
    public ResponseEntity<?> showAllTradersByAscRating(){
        return myUserService.showAllTradersByAscRating();
    }

    @GetMapping("/users/filter_by_game")
    public ResponseEntity<?> showAllTradersByGame(String name){
        return myUserService.showAllTradersByGame(name);
    }


}





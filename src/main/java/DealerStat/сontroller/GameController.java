package DealerStat.сontroller;

import DealerStat.dto.GameDto;
import DealerStat.entity.Game;
import DealerStat.service.GameService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/games")
    public Game createGame(@RequestBody GameDto gameDto){
       return gameService.createGame(gameDto);
    }

    @GetMapping("/games")
    public List<Game> showAllGames(){
        return gameService.showAllGames();
    }

    @PutMapping("/games/{gameId}")
    public Game updateGame(@RequestBody GameDto gameDto, @PathVariable Long gameId) {
        return gameService.updateGame(gameDto, gameId);
    }
}

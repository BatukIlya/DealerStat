package DealerStat.сontroller;

import DealerStat.dto.GameDto;
import DealerStat.entity.Game;
import DealerStat.repository.GameRepository;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api
@RequiredArgsConstructor
public class GameController {

    private final GameRepository gameRepository;

    @PostMapping("/create_game")
    public Game createGame(GameDto gameDto){
        Game game = new Game();
        game.setName(gameDto.getName());
        return gameRepository.save(game);
    }
}

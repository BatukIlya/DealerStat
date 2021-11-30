package DealerStat.сontroller;

import DealerStat.dto.GameDto;
import DealerStat.entity.Game;
import DealerStat.service.GameService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

//    @PreAuthorize("hasAnyAuthority('ADMIN','TRADER')")
    @PostMapping("/games")
    public Game createGame(@RequestBody GameDto gameDto) {
        return gameService.createGame(gameDto);
    }

    @GetMapping("/games")
    public List<Game> showAllGames() {
        return gameService.showAllGames();
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN','TRADER')")
    @PutMapping("/games/{gameId}")
    public ResponseEntity updateGame(@RequestBody GameDto gameDto, @PathVariable Long gameId) {
        return gameService.updateGame(gameDto, gameId);
    }
}

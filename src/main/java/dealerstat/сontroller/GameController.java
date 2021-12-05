package dealerstat.сontroller;

import dealerstat.dto.GameDto;
import dealerstat.entity.Game;
import dealerstat.service.GameService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PreAuthorize("hasAnyAuthority('ADMIN','TRADER')")
    @PostMapping("/games")
    public ResponseEntity<?> createGame(@RequestBody @Valid GameDto gameDto) {
        return gameService.createGame(gameDto);
    }

    @GetMapping("/games")
    public List<Game> showAllGames() {
        return gameService.showAllGames();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','TRADER')")
    @PutMapping("/games/{gameId}")
    public ResponseEntity<?> updateGame(@RequestBody GameDto gameDto, @PathVariable Long gameId) {
        return gameService.updateGame(gameDto, gameId);
    }
}

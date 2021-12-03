package dealerstat.service;

import dealerstat.dto.GameDto;
import dealerstat.entity.Game;
import dealerstat.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public ResponseEntity<?> createGame(GameDto gameDto) {
        if (gameRepository.findGameByNameContainingIgnoreCase(gameDto.getName()).isEmpty()) {
            Game game = new Game();
            game.setName(gameDto.getName());
            gameRepository.save(game);
            return ResponseEntity.ok(game);
        } else {
            return ResponseEntity.status(404).body("This game already exist");
        }

    }

    public List<Game> showAllGames() {
        return gameRepository.findAll();
    }

    public ResponseEntity<?> updateGame(GameDto gameDto, Long id) {
        if (gameRepository.findGameById(id).isPresent()) {
            Game game = gameRepository.findGameById(id).get();
            game.setName(gameDto.getName());
            gameRepository.save(game);
            return ResponseEntity.ok(game);
        } else {
            return ResponseEntity.status(404).body("Game not found");
        }

    }
}

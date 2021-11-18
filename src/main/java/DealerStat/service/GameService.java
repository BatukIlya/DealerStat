package DealerStat.service;

import DealerStat.dto.GameDto;
import DealerStat.entity.Game;
import DealerStat.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public Game createGame(GameDto gameDto) {
        Game game = new Game();
        game.setName(gameDto.getName());
        return gameRepository.save(game);
    }

}

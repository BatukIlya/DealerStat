package DealerStat.service;

import DealerStat.dto.GameDto;
import DealerStat.entity.Game;
import DealerStat.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public Game createGame(GameDto gameDto) {
        Game game = new Game();
        game.setName(gameDto.getName());
        return gameRepository.save(game);
    }

    public List<Game> showAllGames(){
        return gameRepository.findAll();
    }

    public Game updateGame(GameDto gameDto, Long id){
        Game game = gameRepository.findGameById(id);
        game.setName(gameDto.getName());
        return gameRepository.save(game);
    }
}

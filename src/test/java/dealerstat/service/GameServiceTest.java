package dealerstat.service;

import dealerstat.dto.GameDto;
import dealerstat.entity.Game;
import dealerstat.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private GameRepository gameRepository;

    @Test
    public void showAllGamesTest() {
        Game game1 = new Game(1L, "CS");
        Game game2 = new Game(2L, "DOTA");
        Game game3 = new Game(3L, "FORTNITE");

        List<Game> games = new ArrayList<>();

        games.add(game1);
        games.add(game2);
        games.add(game3);

        when(gameRepository.findAll()).thenReturn(games);

        List<Game> allGames = gameService.showAllGames();

        verify(gameRepository, Mockito.times(1)).findAll();
        assertThat(allGames.size()).isEqualTo(3);
        assertThat(allGames.get(0).getName()).isEqualTo("CS");
        assertThat(allGames.get(1).getName()).isEqualTo("DOTA");
        assertThat(allGames.get(2).getName()).isEqualTo("FORTNITE");
    }

    @Test
    public void createGameWhenGameAlreadyExistTest() {
        when(gameRepository.findGameByNameContainingIgnoreCase(any())).thenReturn(Optional.of(new Game()));
        ResponseEntity<?> responseEntity = gameService.createGame(new GameDto());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
        assertThat(responseEntity.getBody()).isEqualTo("This game already exist");

        verify(gameRepository, Mockito.times(1)).findGameByNameContainingIgnoreCase(any());
        verify(gameRepository, Mockito.times(0)).save(new Game());
    }

    @Test
    public void createGameWhenGameNotExistTest() {
        when(gameRepository.findGameByNameContainingIgnoreCase(any())).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = gameService.createGame(new GameDto());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isInstanceOf(Game.class);

        verify(gameRepository, Mockito.times(1)).findGameByNameContainingIgnoreCase(any());
        verify(gameRepository, Mockito.times(1)).save(new Game());
    }

    @Test
    public void updateGameWhenGameAlreadyExistTest() {
        when(gameRepository.findGameById(any())).thenReturn(Optional.of(new Game()));
        ResponseEntity<?> responseEntity = gameService.updateGame(new GameDto(), any());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isInstanceOf(Game.class);


        verify(gameRepository, Mockito.times(2)).findGameById(any());
        verify(gameRepository, Mockito.times(1)).save(new Game());
    }

    @Test
    public void updateGameWhenGameNotExistTest() {
        when(gameRepository.findGameById(any())).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = gameService.updateGame(new GameDto(), any());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
        assertThat(responseEntity.getBody()).isEqualTo("Game not found");

        verify(gameRepository, Mockito.times(1)).findGameById(any());
        verify(gameRepository, Mockito.times(0)).save(new Game());
    }
}
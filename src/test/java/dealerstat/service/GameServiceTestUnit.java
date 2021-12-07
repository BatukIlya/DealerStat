//package dealerstat.service;
//
//import dealerstat.dto.GameDto;
//import dealerstat.entity.Game;
//import dealerstat.repository.GameRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//@RunWith(SpringRunner.class)
//class GameServiceTestUnit {
//
//    @InjectMocks
//    private GameService gameService;
//
//    @Mock
//    private GameRepository gameRepository;
//
//    @Test
//    public void showAllGamesTest() {
//        Game game1 = new Game(1L, "CS");
//        Game game2 = new Game(2L, "DOTA");
//        Game game3 = new Game(3L, "FORTNITE");
//
//        List<Game> games = new ArrayList<>();
//        games.add(game1);
//        games.add(game2);
//        games.add(game3);
//
//        when(gameRepository.findAll()).thenReturn(games);
//
//        List<Game> allGames = gameService.showAllGames();
//
//        assertThat(allGames.size()).isEqualTo(3);
//        assertThat(allGames.get(0).getName()).isEqualTo(game1.getName());
//        assertThat(allGames.get(1).getName()).isEqualTo(game2.getName());
//        assertThat(allGames.get(2).getName()).isEqualTo(game3.getName());
//        verify(gameRepository, Mockito.times(1)).findAll();
//    }
//
//    @Test
//    public void createGameWhenGameAlreadyExistTest() {
//
//        when(gameRepository.findGameByNameContainingIgnoreCase(any())).thenReturn(Optional.of(new Game()));
//        ResponseEntity<?> responseEntity = gameService.createGame(new GameDto());
//
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
//        assertThat(responseEntity.getBody()).isEqualTo("This game already exist");
//
//        verify(gameRepository, Mockito.times(1)).findGameByNameContainingIgnoreCase(any());
//        verify(gameRepository, Mockito.times(0)).save(new Game());
//    }
//
//    @Test
//    public void createGameWhenGameNotExistTest() {
//        GameDto gameDto = new GameDto();
//        gameDto.setName("CS");
//
//        when(gameRepository.findGameByNameContainingIgnoreCase(any())).thenReturn(Optional.empty());
//        ResponseEntity<?> responseEntity = gameService.createGame(gameDto);
//
//        Game game = (Game) responseEntity.getBody();
//
//        assertNotNull(game);
//        assertThat(game.getName()).isEqualTo(gameDto.getName());
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//        assertThat(responseEntity.getBody()).isInstanceOf(Game.class);
//
//        verify(gameRepository, Mockito.times(1)).findGameByNameContainingIgnoreCase(any());
//        verify(gameRepository, Mockito.times(1)).save(game);
//    }
//
//    @Test
//    public void updateGameWhenGameAlreadyExistTest() {
//        Game game = new Game(1L, "DOTA");
//        GameDto gameDto = new GameDto("CS");
//
//        when(gameRepository.findGameById(any())).thenReturn(Optional.of(game));
//        ResponseEntity<?> responseEntity = gameService.updateGame(gameDto, any());
//
//        assertThat(game.getName()).isEqualTo(gameDto.getName());
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//        assertThat(responseEntity.getBody()).isInstanceOf(Game.class);
//
//        verify(gameRepository, Mockito.times(2)).findGameById(any());
//        verify(gameRepository, Mockito.times(1)).save(game);
//    }
//
//    @Test
//    public void updateGameWhenGameNotExistTest() {
//        when(gameRepository.findGameById(any())).thenReturn(Optional.empty());
//        ResponseEntity<?> responseEntity = gameService.updateGame(new GameDto(), any());
//
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
//        assertThat(responseEntity.getBody()).isEqualTo("Game not found");
//
//        verify(gameRepository, Mockito.times(1)).findGameById(any());
//        verify(gameRepository, Mockito.times(0)).save(new Game());
//    }
//}
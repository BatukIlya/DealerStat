//package dealerstat.repository;
//
//import dealerstat.entity.Game;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//class GameRepositoryIntegrationTest {
//
//    @Autowired
//    private GameRepository gameRepository;
//
//    private Game game;
//
//    @BeforeEach
//    public void setup() {
//        game = new Game();
//        game.setName("SomeGameForTest");
//        gameRepository.save(game);
//    }
//
//    @AfterEach
//    public void destroy() {
//        Game gameFetched = gameRepository.findGameByNameContainingIgnoreCase(game.getName()).orElseThrow();
//        gameRepository.deleteById(gameFetched.getId());
//    }
//
//
//    @Test
//    public void findGame() {
//        Game gameFetched1 = gameRepository.findGameByNameContainingIgnoreCase(game.getName()).orElse(null);
//
//        assertThat(gameFetched1).isNotNull();
//        assertThat(gameFetched1).isInstanceOf(Game.class);
//    }
//
//    @Test
//    public void findAllGame() {
//        List<Game> gameList = gameRepository.findAll();
//
//        assertThat(gameList).isNotNull();
//        assertThat(gameList.get(0)).isInstanceOf(Game.class);
//    }
//
//
//}
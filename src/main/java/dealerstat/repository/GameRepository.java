package dealerstat.repository;

import dealerstat.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findGameById(Long id);

    Optional<Game> findGameByName(String name);
}

package DealerStat.repository;

import DealerStat.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GameRepository extends JpaRepository<Game, Long> {
    Game findGameById(Long id);
}

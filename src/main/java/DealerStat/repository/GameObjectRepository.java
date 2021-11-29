package DealerStat.repository;

import DealerStat.entity.GameObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameObjectRepository extends JpaRepository<GameObject, Long> {

    Optional<GameObject> findGameObjectById(Long id);

    Optional<List<GameObject>> findAllByGameId (Long id);

}

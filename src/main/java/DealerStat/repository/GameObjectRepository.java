package DealerStat.repository;

import DealerStat.entity.GameObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameObjectRepository extends JpaRepository<GameObject, Long> {

    GameObject findGameObjectById(Long id);

}

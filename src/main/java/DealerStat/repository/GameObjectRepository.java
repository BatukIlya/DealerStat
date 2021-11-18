package DealerStat.repository;

import DealerStat.entity.GameObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameObjectRepository extends JpaRepository<GameObject, Long> {
}

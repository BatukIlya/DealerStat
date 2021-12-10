package dealerstat.repository;

import dealerstat.entity.GameObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameObjectRepository extends JpaRepository<GameObject, Long> {

    Optional<GameObject> findGameObjectById(Long id);

    Optional<List<GameObject>> findAllByGameId(Long id);

    Optional<List<GameObject>> findAllByAuthorId(Long id);

}

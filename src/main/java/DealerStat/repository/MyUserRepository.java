package DealerStat.repository;

import DealerStat.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findMyUserByIdAndIsApprovedTrue(Long id);

    Optional<MyUser> findMyUserByEmailAndIsApprovedTrue(String email);

    Optional<List<MyUser>> findAllByIsApprovedIsFalse();

    Optional<List<MyUser>> findAllByIsApprovedIsTrue();
}

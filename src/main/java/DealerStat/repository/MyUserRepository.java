package DealerStat.repository;

import DealerStat.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    MyUser findMyUserById(Long id);

    MyUser findMyUserByEmail(String email);

    List<MyUser> findAllByIsApprovedEmailTrueAndIsApprovedIsFalse();
}

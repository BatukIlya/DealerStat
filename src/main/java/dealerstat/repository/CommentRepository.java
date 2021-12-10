package dealerstat.repository;

import dealerstat.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findCommentByIdAndTraderIdAndIsApprovedIsTrue(Long commentId, Long traderId);

    Optional<List<Comment>> findAllByIsApprovedIsFalse();

    Optional<Comment> findCommentById(Long id);

    Optional<List<Comment>> findAllByTraderIdAndIsApprovedIsTrue(Long id);

}

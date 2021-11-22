package DealerStat.repository;

import DealerStat.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findCommentByIdAndIsApprovedTrue(Long id);

    List<Comment> findAllByIsApprovedIsFalse();

    Comment findCommentById(Long id);

    List<Comment> findAllByIsApprovedIsTrue();

    List<Comment> findAllByTraderIdAndIsApprovedIsTrue(Long id);

}

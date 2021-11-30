package dealerstat.service;

import dealerstat.entity.Comment;
import dealerstat.entity.MyUser;
import dealerstat.repository.CommentRepository;
import dealerstat.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AdminService {


    private final CommentRepository commentRepository;

    private final MyUserRepository myUserRepository;

    private final MyUserService myUserService;

    public ResponseEntity findUsersRegistrationRequest() {
        if (myUserRepository.findAllByIsApprovedIsFalse().isPresent()) {
            return ResponseEntity.ok(myUserRepository.findAllByIsApprovedIsFalse().get());
        } else {
            return ResponseEntity.status(204).body("Request list is empty");
        }
    }

    public ResponseEntity approveUser(Long id) {
        if (myUserRepository.findById(id).isPresent()) {
            MyUser myUser = myUserRepository.findById(id).get();
            myUser.setApproved(true);
            myUserService.save(myUser);
            return ResponseEntity.ok("User successfully approved");
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }


    }

    public ResponseEntity approveComment(Long id) {
        if (commentRepository.findCommentById(id).isPresent()) {
            Comment comment = commentRepository.findCommentById(id).get();
            comment.setApproved(true);
            commentRepository.save(comment);
            myUserService.refreshRating(comment.getTrader().getId());
            return ResponseEntity.ok("Comment successfully approved");
        } else {
            return ResponseEntity.badRequest().body("Comment not found");
        }
    }

    public ResponseEntity findCommentsRequest() {
        if (commentRepository.findAllByIsApprovedIsFalse().isPresent()) {
            return ResponseEntity.ok(commentRepository.findAllByIsApprovedIsFalse().get());
        } else {
            return ResponseEntity.status(204).body("Request list is empty");
        }

    }

}

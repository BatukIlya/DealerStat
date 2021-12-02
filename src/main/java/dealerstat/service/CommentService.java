package dealerstat.service;

import dealerstat.dto.CommentDto;
import dealerstat.dto.MyUserDto;
import dealerstat.entity.Comment;
import dealerstat.entity.MyUser;
import dealerstat.entity.Role;
import dealerstat.repository.CommentRepository;
import dealerstat.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final MyUserService myUserService;

    private final JwtTokenProvider jwtTokenProvider;


    public ResponseEntity<?> createComment(CommentDto commentDto, Long traderId, HttpServletRequest request) {
        Comment comment = new Comment();
        comment.setMessage(commentDto.getMessage());
        comment.setAuthor(myUserService.findById(jwtTokenProvider.getId(request)));

        if (myUserService.findMyUserById(traderId) != null) {
            comment.setTrader(myUserService.findMyUserById(traderId));
        } else {
            return ResponseEntity.status(404).body("Trader not found");
        }

        comment.setRating(commentDto.getRating());
        commentRepository.save(comment);
        return ResponseEntity.status(201).body("Comment successfully created.");


    }

    public ResponseEntity<?> createCommentAndTrader(CommentDto commentDto, MyUserDto myUserDto, HttpServletRequest request) {
//            myUserService.registerUser(myUserDto);
        MyUser myUser = myUserService.findMyUserByEmail(myUserDto.getEmail());
        return createComment(commentDto, myUser.getId(), request);

    }


    public ResponseEntity<?> showComment(Long commentId, Long traderId) {
        if (commentRepository.findCommentByIdAndTraderIdAndIsApprovedIsTrue(commentId, traderId).isPresent()) {
            Comment comment = commentRepository.findCommentByIdAndTraderIdAndIsApprovedIsTrue(commentId, traderId).get();
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.status(404).body("Comment not found");
        }
    }

    public ResponseEntity<?> showAll(Long id) {
        if (commentRepository.findAllByTraderIdAndIsApprovedIsTrue(id).isPresent()) {
            return ResponseEntity.ok(commentRepository.findAllByTraderIdAndIsApprovedIsTrue(id).get());
        } else {
            return ResponseEntity.status(404).body("Comments not found");
        }
    }


    public ResponseEntity<?> updateComment(CommentDto commentDto, Long commentId, Long traderId, HttpServletRequest request) {
        if (commentRepository.findCommentByIdAndTraderIdAndIsApprovedIsTrue(commentId, traderId).isPresent()) {
            Comment comment = commentRepository.findCommentByIdAndTraderIdAndIsApprovedIsTrue(commentId, traderId).get();
            if (jwtTokenProvider.getId(request).equals(comment.getAuthor().getId())) {
                if (commentDto.getRating() <= 5 && commentDto.getRating() >= 0) {
                    comment.setMessage(commentDto.getMessage());
                    comment.setRating(commentDto.getRating());
                    commentRepository.save(comment);
                    myUserService.refreshRating(comment.getTrader().getId());
                    return ResponseEntity.ok(comment);
                } else {
                    return ResponseEntity.badRequest().body("Rating should be >0 and <5");
                }
            } else {
                return ResponseEntity.status(403).body("Access denied");
            }
        } else {
            return ResponseEntity.status(404).body("Comment not found");
        }

    }

    public ResponseEntity<?> deleteComment(Long commentId, Long traderId, HttpServletRequest request) {
        if (commentRepository.findCommentByIdAndTraderIdAndIsApprovedIsTrue(commentId, traderId).isPresent()) {
            Comment comment = commentRepository.findCommentByIdAndTraderIdAndIsApprovedIsTrue(commentId, traderId).get();
            if (jwtTokenProvider.getId(request).equals(comment.getAuthor().getId())
                    || (jwtTokenProvider.getRole(request).contains(Role.ADMIN))) {
                commentRepository.deleteById(commentId);
                myUserService.refreshRating(comment.getTrader().getId());
                return ResponseEntity.status(200).body("Comment successfully deleted");
            } else {
                return ResponseEntity.status(403).body("Access denied");
            }
        } else {
            return ResponseEntity.status(404).body("Comment not found");
        }
    }
}

package dealerstat.service;

import dealerstat.config.jwt.JwtTokenProvider;
import dealerstat.dto.CommentDto;
import dealerstat.dto.CreateCommentAndTraderDto;
import dealerstat.entity.Comment;
import dealerstat.entity.MyUser;
import dealerstat.entity.Role;
import dealerstat.repository.CommentRepository;
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

        if (myUserService.findById(traderId) != null && myUserService.findById(jwtTokenProvider.getId(request)) != null) {
            comment.setAuthor(myUserService.findById(jwtTokenProvider.getId(request)));
            comment.setTrader(myUserService.findById(traderId));
            comment.setApproved(false);
        } else {
            return ResponseEntity.status(404).body("User not found");
        }

        comment.setRating(commentDto.getRating());
        commentRepository.save(comment);
        return ResponseEntity.status(201).body(comment);


    }

    public ResponseEntity<?> createCommentAndTrader(CreateCommentAndTraderDto createCommentAndTraderDto,
                                                    HttpServletRequest request) {
        MyUser myUser = new MyUser();
        myUser.setFirstName(createCommentAndTraderDto.getFirstNameTrader());
        myUser.setLastName(createCommentAndTraderDto.getLastNameTrader());
        myUser.setEmail(createCommentAndTraderDto.getEmailTrader());
        myUser.setRating(0.0);
        myUser.setApproved(false);
        myUserService.save(myUser);

        if (myUserService.findMyUserByEmail(createCommentAndTraderDto.getEmailTrader()) != null &&
                myUserService.findById(jwtTokenProvider.getId(request)) != null) {
            MyUser myUser1 = myUserService.findMyUserByEmail(createCommentAndTraderDto.getEmailTrader());

            Comment comment = new Comment();
            comment.setMessage(createCommentAndTraderDto.getMessageComment());
            comment.setAuthor(myUserService.findById(jwtTokenProvider.getId(request)));
            comment.setTrader(myUserService.findById(myUser1.getId()));
            comment.setRating(createCommentAndTraderDto.getRatingComment());
            comment.setApproved(false);
            commentRepository.save(comment);

            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.status(404).body("User not found");
        }


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

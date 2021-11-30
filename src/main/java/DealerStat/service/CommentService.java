package DealerStat.service;

import DealerStat.dto.CommentDto;
import DealerStat.dto.MyUserDto;
import DealerStat.entity.Comment;
import DealerStat.entity.MyUser;
import DealerStat.entity.Role;
import DealerStat.repository.CommentRepository;
import DealerStat.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final MyUserService myUserService;

    private final JwtTokenProvider jwtTokenProvider;


    public ResponseEntity createComment(CommentDto commentDto, Long traderId, HttpServletRequest request) {
        if (commentDto.getRating() <= 5.0 && commentDto.getRating() >= 0) {
            Comment comment = new Comment();
            comment.setMessage(commentDto.getMessage());
            comment.setAuthor(myUserService.findById(jwtTokenProvider.getId(request)));
            comment.setTrader(myUserService.findMyUserById(traderId));
            comment.setRating(commentDto.getRating());
            commentRepository.save(comment);
            return ResponseEntity.status(201).body("Comment successfully created.");
        } else {
            return ResponseEntity.badRequest().body("Rating should be >0 and <5");
        }

    }

    public ResponseEntity createCommentAndTrader(CommentDto commentDto, MyUserDto myUserDto, HttpServletRequest request) {
        if (commentDto.getRating() <= 5.0 && commentDto.getRating() >= 0) {
//            myUserService.registerUser(myUserDto);
            MyUser myUser = myUserService.findMyUserByEmail(myUserDto.getEmail());
            return createComment(commentDto, myUser.getId(), request);
        } else {
            return ResponseEntity.badRequest().body("Rating must be >0 and <5");
        }
    }


    public ResponseEntity showComment(Long id) {
        if (commentRepository.findCommentByIdAndIsApprovedTrue(id).isPresent()) {
            Comment comment = commentRepository.findCommentByIdAndIsApprovedTrue(id).get();
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.status(404).body("Comment not found");
        }
    }

    public ResponseEntity showAll(Long id) {
        if(commentRepository.findAllByTraderIdAndIsApprovedIsTrue(id).isPresent()){
            return ResponseEntity.ok(commentRepository.findAllByTraderIdAndIsApprovedIsTrue(id).get());
        }else{
            return ResponseEntity.status(404).body("Comments not found");
        }
    }


    public ResponseEntity updateComment(CommentDto commentDto, Long id, HttpServletRequest request) {
        if (commentRepository.findCommentById(id).isPresent()) {
            Comment comment = commentRepository.findCommentById(id).get();
            if (jwtTokenProvider.getId(request).equals(comment.getAuthor().getId())) {
                if (commentDto.getRating() <= 5 && commentDto.getRating() >= 0) {
                    comment.setMessage(commentDto.getMessage());
                    comment.setRating(commentDto.getRating());
                    commentRepository.save(comment);
                    myUserService.refreshRating(comment.getId());
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

    public ResponseEntity deleteComment(Long id, HttpServletRequest request) {
        if (commentRepository.findCommentById(id).isPresent()) {
            Comment comment = commentRepository.findCommentById(id).get();
            if (jwtTokenProvider.getId(request).equals(comment.getAuthor().getId())
                    || (jwtTokenProvider.getRole(request).contains(Role.ADMIN))) {
                commentRepository.deleteById(id);
                myUserService.refreshRating(comment.getTrader().getId());
                return ResponseEntity.status(200).body("Comment successfully deleted");
            } else {
                return ResponseEntity.status(403).body("Access denied");
            }
        }else{
            return ResponseEntity.status(404).body("Comment not found");
        }
    }
}

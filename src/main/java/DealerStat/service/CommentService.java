package DealerStat.service;

import DealerStat.dto.CommentDto;
import DealerStat.dto.MyUserDto;
import DealerStat.entity.Comment;
import DealerStat.entity.MyUser;
import DealerStat.entity.Role;
import DealerStat.repository.CommentRepository;
import DealerStat.repository.MyUserRepository;
import DealerStat.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final MyUserRepository myUserRepository;

    private final MyUserService myUserService;

    private final JwtTokenProvider jwtTokenProvider;



    public Comment createComment(CommentDto commentDto, Long traderId, HttpServletRequest request) {
        if (commentDto.getRating() <= 5.0 && commentDto.getRating() >= 0) {
            Comment comment = new Comment();
            comment.setMessage(commentDto.getMessage());
            comment.setAuthor(myUserRepository.findMyUserById(jwtTokenProvider.getId(request)));
            comment.setTrader(myUserRepository.findMyUserById(traderId));
            comment.setRating(commentDto.getRating());
            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Rating must be >0 and <5");
        }

    }

    public Comment createCommentAndTrader(CommentDto commentDto, MyUserDto myUserDto, HttpServletRequest request) {
        if (commentDto.getRating() <= 5.0 && commentDto.getRating() >= 0) {
//            myUserService.registerUser(myUserDto);
            MyUser myUser = myUserRepository.findMyUserByEmail(myUserDto.getEmail());
            return createComment(commentDto, myUser.getId(), request);
        } else {
            throw new RuntimeException("Rating must be >0 and <5");
        }
    }

    public Comment showComment(Long id) {
        return commentRepository.findCommentById(id);
    }

    public List<Comment> showAll(Long id) {
        return commentRepository.findAllByTraderIdAndIsApprovedIsTrue(id);
    }


    public Comment updateComment(CommentDto commentDto, Long id) {
        if (commentDto.getRating() <= 5 && commentDto.getRating() >= 0) {
            Comment comment = commentRepository.findCommentById(id);
            comment.setMessage(commentDto.getMessage());
            comment.setRating(commentDto.getRating());
            commentRepository.save(comment);
            myUserService.refreshRating(comment.getId());
            return comment;
        } else {
            throw new RuntimeException("Rating should be >0 and <5");
        }
    }

    public void deleteComment(Long id, HttpServletRequest request) {
        Comment comment = commentRepository.findCommentById(id);
        if (jwtTokenProvider.getId(request).equals(comment.getAuthor().getId())
                || (jwtTokenProvider.getRole(request).contains(Role.ADMIN))) {
            commentRepository.deleteById(id);
            myUserService.refreshRating(comment.getTrader().getId());
        } else {
            throw new RuntimeException("Access denied");
        }
    }
}

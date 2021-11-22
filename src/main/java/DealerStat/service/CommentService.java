package DealerStat.service;

import DealerStat.dto.CommentDto;
import DealerStat.dto.MyUserDto;
import DealerStat.entity.Comment;
import DealerStat.entity.MyUser;
import DealerStat.repository.CommentRepository;
import DealerStat.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final MyUserRepository myUserRepository;

    private final MyUserService myUserService;

    public Comment createComment(CommentDto commentDto, Long traiderId) {
        Comment comment = new Comment();
        comment.setMessage(commentDto.getMessage());
        comment.setTrader(myUserRepository.findMyUserById(traiderId));
        return commentRepository.save(comment);
    }

    public Comment createCommentAndTrader(CommentDto commentDto, MyUserDto myUserDto){
        myUserService.createUser(myUserDto);
        Comment comment = new Comment();
        comment.setMessage(commentDto.getMessage());
        comment.setTrader(myUserRepository.findMyUserByFirstName(myUserDto.getFirstName()));
        return commentRepository.save(comment);
    }

    public Comment showComment(Long id) {
        return commentRepository.findCommentById(id);
    }

    public List<Comment> showAll(Long id) {
        return commentRepository.findAllByTraderIdAndIsApprovedIsTrue(id);
    }

    public Comment approveComment(Long id) {
        Comment comment = commentRepository.findCommentById(id);
        comment.setApproved(true);
        return commentRepository.save(comment);
    }

    public Comment updateComment(String message, Long id) {
        Comment comment = commentRepository.findCommentById(id);
        comment.setMessage(message);
        comment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {

        commentRepository.deleteById(id);
    }

}

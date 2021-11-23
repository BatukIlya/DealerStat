package DealerStat.service;

import DealerStat.config.AuthProvider;
import DealerStat.dto.CommentDto;
import DealerStat.dto.MyUserDto;
import DealerStat.entity.Comment;
import DealerStat.entity.MyUser;
import DealerStat.repository.CommentRepository;
import DealerStat.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final MyUserRepository myUserRepository;

    private final MyUserService myUserService;

    public Comment createComment(CommentDto commentDto, Long traderId) {
        Comment comment = new Comment();
        MyUser myUser1 = myUserRepository.findMyUserByFirstName(SecurityContextHolder.getContext().getAuthentication().getName());
        comment.setMessage(commentDto.getMessage());
        comment.setAuthor(myUser1);
        comment.setTrader(myUserRepository.findMyUserById(traderId));
        return commentRepository.save(comment);
    }

    public Comment createCommentAndTrader(CommentDto commentDto, MyUserDto myUserDto) {
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

        if (myUserRepository.findMyUserByFirstName(SecurityContextHolder.getContext()
        .getAuthentication().getName()).getId() == commentRepository.findCommentById(id).getAuthor().getId()) {
            commentRepository.deleteById(id);
        }else{
            throw new RuntimeException("Access denied");
        }
    }}

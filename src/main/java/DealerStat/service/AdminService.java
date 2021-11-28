package DealerStat.service;

import DealerStat.entity.Comment;
import DealerStat.entity.MyUser;
import DealerStat.repository.CommentRepository;
import DealerStat.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MyUserRepository myUserRepository;

    private final CommentRepository commentRepository;

    private final MyUserService myUserService;

    public List<MyUser> findUsersRegistrationRequest(){
        return myUserRepository.findAllByIsApprovedEmailTrueAndIsApprovedIsFalse();
    }

    public MyUser approveUser(Long id){
        MyUser myUser = myUserRepository.findMyUserById(id);
        myUser.setApproved(true);
        return myUserRepository.save(myUser);
    }

    public Comment approveComment(Long id) {
        Comment comment = commentRepository.findCommentById(id);
        comment.setApproved(true);
        commentRepository.save(comment);
        myUserService.refreshRating(comment.getTrader().getId());
        return comment;
    }

    public List<Comment> findCommentsRequest(){
        return commentRepository.findAllByIsApprovedIsFalse();
    }

}

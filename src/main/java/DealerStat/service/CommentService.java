package DealerStat.service;

import DealerStat.dto.CommentDto;
import DealerStat.entity.Comment;
import DealerStat.repository.CommentRepository;
import DealerStat.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final MyUserRepository myUserRepository;

    public Comment createComment (CommentDto commentDto, Long traiderId){
        Comment comment = new Comment();
        comment.setMessage(commentDto.getMessage());
        comment.setTrader(myUserRepository.findByIdAndIsApprovedIsTrue(traiderId));
        return commentRepository.save(comment);
    }

}

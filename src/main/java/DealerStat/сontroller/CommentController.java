package DealerStat.сontroller;

import DealerStat.dto.CommentDto;
import DealerStat.entity.Comment;
import DealerStat.service.CommentService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/createComment/{traderId}")
    public Comment createComment (@RequestBody CommentDto commentDto, @PathVariable Long traderId){
        return commentService.createComment(commentDto, traderId);
    }



}

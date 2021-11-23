package DealerStat.сontroller;

import DealerStat.dto.CommentDto;
import DealerStat.dto.MyUserDto;
import DealerStat.entity.Comment;
import DealerStat.entity.MyUser;
import DealerStat.service.CommentService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Api
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/create/{traderId}")
    public Comment createComment(@RequestBody CommentDto commentDto, @PathVariable Long traderId) {
        return commentService.createComment(commentDto, traderId);
    }

    @PostMapping("/create_comment_and_trader")
    public Comment createCommentAndTrader(@RequestBody CommentDto commentDto, MyUserDto myUserDto){
        return commentService.createCommentAndTrader(commentDto, myUserDto);
    }

    @GetMapping("/comment/{commentId}")
    public Comment showComment(@PathVariable Long commentId) {
        return commentService.showComment(commentId);
    }

    @GetMapping("/comment/show/{traderId}")
    public List<Comment> showCommentsTrader(@PathVariable Long traderId) {
        return commentService.showAll(traderId);
    }

    @PutMapping("/comment/approve/{commentId}/")
    public Comment approveComment(@PathVariable Long commentId) {
        return commentService.approveComment(commentId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @DeleteMapping("/comment/delete/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }

    @PutMapping("/comment/{commentId}/update")
    public Comment updateComment(CommentDto commentDto, @PathVariable Long commentId){
        return commentService.updateComment(commentDto.getMessage(), commentId);
    }



}

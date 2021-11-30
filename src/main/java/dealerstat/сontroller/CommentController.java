package dealerstat.сontroller;

import dealerstat.dto.CommentDto;
import dealerstat.dto.MyUserDto;
import dealerstat.service.CommentService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/comment/create/{traderId}")
    public ResponseEntity createComment(@RequestBody CommentDto commentDto, @PathVariable Long traderId, HttpServletRequest request) {
        return commentService.createComment(commentDto, traderId, request);
    }

    @PostMapping("/create_comment_and_trader")
    public ResponseEntity createCommentAndTrader(@RequestBody CommentDto commentDto, MyUserDto myUserDto, HttpServletRequest request){
        return commentService.createCommentAndTrader(commentDto, myUserDto, request);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity showComment(@PathVariable Long commentId) {
        return commentService.showComment(commentId);
    }

    @GetMapping("/comment/show/{traderId}")
    public ResponseEntity showCommentsTrader(@PathVariable Long traderId) {
        return commentService.showAll(traderId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','TRADER')")
    @DeleteMapping("/comment/delete/{commentId}")
    public void deleteComment(@PathVariable Long commentId, HttpServletRequest request) {
        commentService.deleteComment(commentId, request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TRADER')")
    @PutMapping("/comment/{commentId}/update")
    public ResponseEntity updateComment(@RequestBody CommentDto commentDto, @PathVariable Long commentId, HttpServletRequest request){
        return commentService.updateComment(commentDto, commentId, request);
    }



}

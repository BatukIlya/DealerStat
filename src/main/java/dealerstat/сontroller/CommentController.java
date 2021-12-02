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
import javax.validation.Valid;

@RestController
@Api
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/users/{traderId}/comments")
    public ResponseEntity<?> createComment(@RequestBody @Valid CommentDto commentDto, @PathVariable Long traderId, HttpServletRequest request) {
        return commentService.createComment(commentDto, traderId, request);
    }

    @PostMapping("/users/create_comment_and_trader")
    public ResponseEntity<?> createCommentAndTrader(@RequestBody @Valid CommentDto commentDto, MyUserDto myUserDto, HttpServletRequest request){
        return commentService.createCommentAndTrader(commentDto, myUserDto, request);
    }

    @GetMapping("/users/{traderId}/comments/{commentId}")
    public ResponseEntity<?> showComment(@PathVariable Long commentId, @PathVariable Long traderId) {
        return commentService.showComment(commentId, traderId);
    }

    @GetMapping("/users/{traderId}/comments")
    public ResponseEntity<?> showCommentsTrader(@PathVariable Long traderId) {
        return commentService.showAll(traderId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','TRADER')")
    @DeleteMapping("/users/{traderId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId, @PathVariable Long traderId, HttpServletRequest request) {
        return commentService.deleteComment(commentId, traderId, request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TRADER')")
    @PutMapping("/users/{traderId}/comments/{commentId}")
    public ResponseEntity<?> updateComment(@RequestBody CommentDto commentDto,
                                        @PathVariable Long commentId, @PathVariable Long traderId, HttpServletRequest request){
        return commentService.updateComment(commentDto, commentId, traderId, request);
    }



}

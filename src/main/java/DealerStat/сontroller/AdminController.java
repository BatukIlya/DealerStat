package DealerStat.сontroller;

import DealerStat.entity.Comment;
import DealerStat.entity.MyUser;
import DealerStat.service.AdminService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/registration_request/")
    public List<MyUser> showUsersRegistrationRequest(){
       return adminService.findUsersRegistrationRequest();
    }

    @PutMapping("/registration_request/{id}")
    public MyUser approveUser(@PathVariable Long id){
        return adminService.approveUser(id);
    }

    @GetMapping("/comment_request")
    public List<Comment> showCommentRequest(){
        return adminService.findCommentsRequest();
    }

    @PutMapping("/comment_request/{commentId}/")
    public Comment approveComment(@PathVariable Long commentId) {
        return adminService.approveComment(commentId);
    }
}

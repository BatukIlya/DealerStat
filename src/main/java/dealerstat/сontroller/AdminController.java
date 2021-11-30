package dealerstat.сontroller;

import dealerstat.service.AdminService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Api
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin/")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/registration_request/")
    public ResponseEntity<?> showUsersRegistrationRequest(){
       return adminService.findUsersRegistrationRequest();
    }

    @PutMapping("/registration_request/{id}")
    public ResponseEntity<?> approveUser(@PathVariable Long id){
        return adminService.approveUser(id);
    }

    @GetMapping("/comment_request")
    public ResponseEntity<?> showCommentRequest(){
        return adminService.findCommentsRequest();
    }

    @PutMapping("/comment_request/{commentId}/")
    public ResponseEntity<?> approveComment(@PathVariable Long commentId) {
        return adminService.approveComment(commentId);
    }
}

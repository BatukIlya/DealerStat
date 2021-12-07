//package dealerstat.service;
//
//import dealerstat.entity.MyUser;
//import dealerstat.repository.MyUserRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//@RunWith(SpringRunner.class)
//class AdminServiceTestUnit {
//
//    @InjectMocks
//    private AdminService adminService;
//
//    @Mock
//    private MyUserRepository myUserRepository;
//
//    @Mock
//    private MyUserService myUserService;
//
//
//    @Test
//    void findUsersRegistrationRequestIfExistTest() {
//        List<MyUser> myUsersRequest = new ArrayList<>();
//        myUsersRequest.add(new MyUser());
//        myUsersRequest.add(new MyUser());
//        myUsersRequest.add(new MyUser());
//
//
//        when(myUserRepository.findAllByIsApprovedIsFalse()).thenReturn(Optional.of(myUsersRequest));
//        ResponseEntity<?> responseEntity = adminService.findUsersRegistrationRequest();
//
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//        assertThat(responseEntity.getBody()).isInstanceOf(List.class);
//
//        verify(myUserRepository, Mockito.times(2)).findAllByIsApprovedIsFalse();
//    }
//
//    @Test
//    void findUsersRegistrationRequestNotExistTest() {
//        when(myUserRepository.findAllByIsApprovedIsFalse()).thenReturn(Optional.empty());
//        ResponseEntity<?> responseEntity = adminService.findUsersRegistrationRequest();
//
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
//
//        assertThat(responseEntity.getBody()).isEqualTo("Request list is empty");
//
//        verify(myUserRepository, Mockito.times(1)).findAllByIsApprovedIsFalse();
//    }
//
//    @Test
//    void approveUserIfExist() {
//        MyUser myUser = new MyUser();
//        myUser.setApproved(false);
//
//        when(myUserRepository.findById(any())).thenReturn(Optional.of(myUser));
//
//        ResponseEntity<?> responseEntity = adminService.approveUser(any());
//
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//        assertTrue(myUser.isApproved());
//
//        verify(myUserRepository, Mockito.times(2)).findById(any());
//        verify(myUserService, Mockito.times(1)).save(myUser);
//        verify(myUserService, Mockito.times(1)).refreshRating(any());
//
//    }
//
//    @Test
//    void approveUserNotExist() {
//        when(myUserRepository.findById(any())).thenReturn(Optional.empty());
//
//        ResponseEntity<?> responseEntity = adminService.approveUser(any());
//
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
//        assertThat(responseEntity.getBody()).isEqualTo("User not found");
//
//        verify(myUserRepository, Mockito.times(1)).findById(any());
//        verify(myUserService, Mockito.times(0)).save(new MyUser());
//        verify(myUserService, Mockito.times(0)).refreshRating(any());
//
//    }
//}
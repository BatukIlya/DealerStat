//package dealerstat.repository;
//
//import dealerstat.entity.MyUser;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//class MyUserRepositoryIntegrationTest {
//
//    @Autowired
//    private MyUserRepository myUserRepository;
//
//    private MyUser myUser;
//
//    @BeforeEach
//    public void setup(){
//        myUser = new MyUser();
//        myUser.setApproved(false);
//        myUser.setRating(2.0);
//        myUser.setEmail("someEmail");
//        myUser.setLastName("Some Name");
//        myUser.setFirstName("Some Name");
//        myUserRepository.save(myUser);
//    }
//
//    @AfterEach
//    public void destroy(){
//        MyUser myUser1 = myUserRepository.findMyUserByEmail(myUser.getEmail()).orElseThrow();
//        myUserRepository.deleteById(myUser1.getId());
//    }
//
//    @Test
//    public void findMyUserByEmail() {
//        MyUser myUserFetched = myUserRepository.findMyUserByEmail(myUser.getEmail()).orElse(null);
//
//        assertThat(myUserFetched).isNotNull();
//        assertThat(myUserFetched).isInstanceOf(MyUser.class);
//    }
//
//    @Test
//    public void findAllByIsApprovedIsFalse() {
//        List<MyUser> myUsers = myUserRepository.findAllByIsApprovedIsFalse().orElse(null);
//
//        assertThat(myUsers).isNotNull();
//        assertThat(myUsers.size()).isGreaterThanOrEqualTo(1);
//    }
//
//    @Test
//    public void findAllByIsApprovedIsTrue() {
//        MyUser myUserFetched = myUserRepository.findMyUserByEmail(myUser.getEmail()).orElse(null);
//
//        assertThat(myUserFetched).isNotNull();
//
//        myUserFetched.setApproved(true);
//        myUserRepository.save(myUserFetched);
//
//        List<MyUser> myUsers = myUserRepository.findAllByIsApprovedIsTrue().orElse(null);
//
//        assertThat(myUsers).isNotNull();
//        assertThat(myUsers.size()).isGreaterThanOrEqualTo(1);
//    }
//
//}
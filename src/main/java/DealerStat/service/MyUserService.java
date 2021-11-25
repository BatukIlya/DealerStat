package DealerStat.service;

import DealerStat.dto.MyUserDto;
import DealerStat.entity.Comment;
import DealerStat.entity.MyUser;
import DealerStat.entity.Role;
import DealerStat.repository.CommentRepository;
import DealerStat.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
//@RequiredArgsConstructor
public class MyUserService{

    private final MyUserRepository myUserRepository;

//    private final CommentRepository commentRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public MyUserService(MyUserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.myUserRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public MyUser register(MyUser user) {
//        Role roleUser = roleRepository.findByName("ROLE_USER");
//        List<Role> userRoles = new ArrayList<>();
//        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(Role.USER));


        MyUser registeredUser = myUserRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }


    public List<MyUser> getAll() {
        List<MyUser> result = myUserRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }


    public MyUser findByUsername(String username) {
        MyUser result = myUserRepository.findMyUserByEmail(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }


    public MyUser findById(Long id) {
        MyUser result = myUserRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result);
        return result;
    }


    public void delete(Long id) {
        myUserRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted");
    }
}










//    public MyUser createUser(MyUserDto myUserDto) {
//        if (myUserRepository.findMyUserByEmail(myUserDto.getEmail()) == null) {
//            MyUser myUser = new MyUser();
//            myUser.setFirstName(myUserDto.getFirstName());
//            myUser.setLastName(myUserDto.getLastName());
//            myUser.setPassword(passwordEncoder.encode(myUserDto.getPassword()));
//            myUser.setEmail(myUserDto.getEmail());
//            myUser.setRoles(Collections.singleton(Role.USER));
//            return myUserRepository.save(myUser);
//        }else{
//            throw new RuntimeException("Duplicate user");
//        }
//    }
//
//
//
//    public Long getIdAuthorizedUser() {
//        MyUser myUser = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return myUser.getId();
//    }
//
//    public boolean checkRole(Role role) {
//        Set<Role> roles = Collections.singleton(role);
//        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().equals(roles.toString());
//
//    }
//
//    public MyUser refreshRating(Long id){
//        Double ratingTrader = commentRepository.findAllByTraderIdAndIsApprovedIsTrue(id).stream().mapToDouble(Comment::getRating)
//                .average().orElse(0);
//        MyUser myUser = myUserRepository.findMyUserById(id);
//        myUser.setRating(ratingTrader);
//        return myUserRepository.save(myUser);
//    }


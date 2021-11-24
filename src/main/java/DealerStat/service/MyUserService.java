package DealerStat.service;

import DealerStat.dto.MyUserDto;
import DealerStat.entity.Comment;
import DealerStat.entity.MyUser;
import DealerStat.entity.Role;
import DealerStat.repository.CommentRepository;
import DealerStat.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MyUserService implements UserDetailsService {

    private final MyUserRepository myUserRepository;

    private final CommentRepository commentRepository;

    private final PasswordEncoder passwordEncoder;

    public MyUser createUser(MyUserDto myUserDto) {
        if (myUserRepository.findMyUserByEmail(myUserDto.getEmail()) == null) {
            MyUser myUser = new MyUser();
            myUser.setFirstName(myUserDto.getFirstName());
            myUser.setLastName(myUserDto.getLastName());
            myUser.setPassword(passwordEncoder.encode(myUserDto.getPassword()));
            myUser.setEmail(myUserDto.getEmail());
            myUser.setRoles(Collections.singleton(Role.USER));
            return myUserRepository.save(myUser);
        }else{
            throw new RuntimeException("Duplicate user");
        }
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MyUser userFindByEmail = myUserRepository.findMyUserByEmail(email);

        if (userFindByEmail != null) {
            return userFindByEmail;
        }
        return null;
    }

    public Long getIdAuthorizedUser() {
        MyUser myUser = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return myUser.getId();
    }

    public boolean checkRole(Role role) {
        Set<Role> roles = Collections.singleton(role);
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().equals(roles.toString());

    }

    public MyUser refreshRating(Long id){
        Double ratingTrader = commentRepository.findAllByTraderIdAndIsApprovedIsTrue(id).stream().mapToDouble(Comment::getRating)
                .average().orElse(0);
        MyUser myUser = myUserRepository.findMyUserById(id);
        myUser.setRating(ratingTrader);
        return myUserRepository.save(myUser);
    }
}

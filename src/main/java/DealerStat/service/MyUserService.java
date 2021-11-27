package DealerStat.service;

import DealerStat.dto.MyUserDto;
import DealerStat.entity.Comment;
import DealerStat.entity.MyUser;
import DealerStat.entity.Role;
import DealerStat.repository.CommentRepository;
import DealerStat.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyUserService {

    private final MyUserRepository myUserRepository;

    private final CommentRepository commentRepository;

    private final BCryptPasswordEncoder passwordEncoder;


    public MyUser registerUser(MyUserDto myUserDto) {
        if (myUserRepository.findMyUserByEmail(myUserDto.getEmail()) == null) {
            MyUser myUser = new MyUser();
            myUser.setFirstName(myUserDto.getFirstName());
            myUser.setLastName(myUserDto.getLastName());
            myUser.setPassword(passwordEncoder.encode(myUserDto.getPassword()));
            myUser.setEmail(myUserDto.getEmail());
            myUser.setRoles(Collections.singletonList(Role.USER));
            return myUserRepository.save(myUser);
        } else {
            log.info("User with this email already exists");
            return null;
        }
    }


    public MyUser findMyUserByEmail(String username) {
        MyUser result = myUserRepository.findMyUserByEmail(username);
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


    public MyUser refreshRating(Long id) {
        Double ratingTrader = commentRepository.findAllByTraderIdAndIsApprovedIsTrue(id).stream().mapToDouble(Comment::getRating)
                .average().orElse(0);
        MyUser myUser = myUserRepository.findMyUserById(id);
        myUser.setRating(ratingTrader);
        return myUserRepository.save(myUser);
    }
}


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


    public MyUser findMyUserByEmail(String username) {
        return myUserRepository.findMyUserByEmail(username);
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

    public MyUser save(MyUser myUser){
        return myUserRepository.save(myUser);
    }


    public MyUser refreshRating(Long id) {
        Double ratingTrader = commentRepository.findAllByTraderIdAndIsApprovedIsTrue(id).stream().mapToDouble(Comment::getRating)
                .average().orElse(0);
        MyUser myUser = myUserRepository.findMyUserById(id);
        myUser.setRating(ratingTrader);
        return myUserRepository.save(myUser);
    }
}


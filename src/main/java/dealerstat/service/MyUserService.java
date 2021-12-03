package dealerstat.service;

import dealerstat.dto.SearchCriteria;
import dealerstat.entity.Comment;
import dealerstat.entity.MyUser;
import dealerstat.repository.CommentRepository;
import dealerstat.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyUserService {

    private final MyUserRepository myUserRepository;

    private final CommentRepository commentRepository;


    public MyUser findMyUserByEmail(String username) {
        return myUserRepository.findMyUserByEmail(username).orElse(null);
    }

    public MyUser findMyUserById(Long id) {
        return myUserRepository.findMyUserByIdAndIsApprovedTrue(id).orElse(null);
    }

    public MyUser findById(Long id) {
        return myUserRepository.findById(id).orElse(null);
    }

    public void save(MyUser myUser) {
        myUserRepository.save(myUser);
    }

    public ResponseEntity<?> showAllTraders(SearchCriteria searchCriteria) {
        List<MyUser> myUsers = new ArrayList<>();

        if (searchCriteria.getGame() != null) {
            System.out.println(1);
        } else if (myUserRepository.findAllByIsApprovedIsTrue().isPresent()) {
            myUsers = myUserRepository.findAllByIsApprovedIsTrue().get();
        } else {
            return ResponseEntity.status(404).body("Users not found");
        }

        if (searchCriteria.isSortByAsc()) {
            myUsers.sort(Comparator.comparing(MyUser::getRating));
        } else {
            myUsers.sort(Comparator.comparing(MyUser::getRating, Comparator.reverseOrder()));
        }

        Integer count = searchCriteria.getCount();
        if (count != null) {
            if (count > myUsers.size()) {
                count = myUsers.size();
            }
            myUsers.subList(count, myUsers.size()).clear();
        }

        return ResponseEntity.ok(myUsers);
    }


    public void refreshRating(Long id) {
        double ratingTrader;
        if (commentRepository.findAllByTraderIdAndIsApprovedIsTrue(id).isPresent()) {
            List<Comment> comments = commentRepository.findAllByTraderIdAndIsApprovedIsTrue(id).get();
            ratingTrader = comments.stream().mapToDouble(Comment::getRating).average().orElse(0);
        } else {
            ratingTrader = 0.0;
        }

        if (myUserRepository.findMyUserByIdAndIsApprovedTrue(id).isPresent()) {
            MyUser myUser = myUserRepository.findMyUserByIdAndIsApprovedTrue(id).get();
            myUser.setRating(ratingTrader);
            myUserRepository.save(myUser);
        }

    }
}


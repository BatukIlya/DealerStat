package dealerstat.service;

import dealerstat.dto.SearchCriteria;
import dealerstat.entity.Comment;
import dealerstat.entity.GameObject;
import dealerstat.entity.MyUser;
import dealerstat.repository.CommentRepository;
import dealerstat.repository.GameObjectRepository;
import dealerstat.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyUserService {

    private final MyUserRepository myUserRepository;

    private final CommentRepository commentRepository;

    private final GameObjectRepository gameObjectRepository;


    public MyUser findMyUserByEmail(String username) {
        return myUserRepository.findMyUserByEmail(username).orElse(null);
    }

    public ResponseEntity<?> findMyUserById(Long id) {
        if (myUserRepository.findMyUserByIdAndIsApprovedTrue(id).isPresent()) {
            return ResponseEntity.ok(myUserRepository.findMyUserByIdAndIsApprovedTrue(id).get());
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    public MyUser findById(Long id) {
        return myUserRepository.findById(id).orElse(null);
    }

    public void save(MyUser myUser) {
        myUserRepository.save(myUser);
    }

    public ResponseEntity<?> showAllTraders(SearchCriteria searchCriteria) {
        List<MyUser> myUsers;

        if (searchCriteria.getGame() != null) {

            if (gameObjectRepository.findAllByGameId(searchCriteria.getGame().getId()).isPresent()) {
                List<GameObject> gameObjects = gameObjectRepository.findAllByGameId(searchCriteria.getGame().getId()).get();
                myUsers = gameObjects.stream().map(GameObject::getAuthor).distinct().collect(Collectors.toList());
            }else{
                return ResponseEntity.status(404).body("Game not found");
            }

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
        if (count != null && count != 0) {
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


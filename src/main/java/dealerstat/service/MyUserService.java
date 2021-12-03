package dealerstat.service;

import dealerstat.dto.SearchCriteria;
import dealerstat.entity.Comment;
import dealerstat.entity.GameObject;
import dealerstat.entity.MyUser;
import dealerstat.repository.CommentRepository;
import dealerstat.repository.GameObjectRepository;
import dealerstat.repository.GameRepository;
import dealerstat.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyUserService {

    private final MyUserRepository myUserRepository;

    private final CommentRepository commentRepository;

    private final GameObjectRepository gameObjectRepository;

    private final GameRepository gameRepository;


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

    public ResponseEntity<?> showAllTraders(){
        if(myUserRepository.findAllByIsApprovedIsTrue().isPresent()){
            List<MyUser> traders = myUserRepository.findAllByIsApprovedIsTrue().get();
            return ResponseEntity.ok(traders);
        }else{
            return ResponseEntity.status(404).body("Traders not found");
        }
    }

    public ResponseEntity<?> showAllTradersByDescRating() {
        if (myUserRepository.findAllByIsApprovedIsTrue().isPresent()) {
            List<MyUser> myUsers = myUserRepository.findAllByIsApprovedIsTrue().get();
            myUsers.sort(Comparator.comparing(MyUser::getRating));
            return ResponseEntity.ok(myUsers);
        } else {
            return ResponseEntity.status(404).body("Users not found");
        }

    }

    public ResponseEntity<?> showAllTradersByAscRating() {
        if (myUserRepository.findAllByIsApprovedIsTrue().isPresent()) {
            List<MyUser> myUsers = myUserRepository.findAllByIsApprovedIsTrue().get();
            myUsers.sort(Comparator.comparing(MyUser::getRating, Comparator.reverseOrder()));
            return ResponseEntity.ok(myUsers);
        } else {
            return ResponseEntity.status(404).body("Users not found");
        }

    }

    public ResponseEntity<?> showAllTradersByGame(String name) {
        long id;
        if (gameRepository.findGameByName(name).isPresent()) {
            id = gameRepository.findGameByName(name).get().getId();
        } else {
            return ResponseEntity.status(404).body("Game not found");
        }

        if (gameObjectRepository.findAllByGameId(id).isPresent()) {
            List<GameObject> gameObjects = gameObjectRepository.findAllByGameId(id).get();
            List<MyUser> myUsers = gameObjects.stream().map(GameObject::getAuthor).distinct().collect(Collectors.toList());
            return ResponseEntity.ok(myUsers);
        } else {
            return ResponseEntity.status(404).body("No one users have been found for this game");
        }

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


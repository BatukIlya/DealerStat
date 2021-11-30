package dealerstat.service;

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
        return myUserRepository.findById(id).get();
    }


    public MyUser save(MyUser myUser) {
        return myUserRepository.save(myUser);
    }

    public ResponseEntity showAllTradersByDescRating() {
        if (myUserRepository.findAllByIsApprovedIsTrue().isPresent()) {
            List<MyUser> myUsers = myUserRepository.findAllByIsApprovedIsTrue().get();
            myUsers.sort(Comparator.comparing(MyUser::getRating));
            return ResponseEntity.ok(myUsers);
        } else {
            return ResponseEntity.status(404).body("Users not found");
        }

    }

    public ResponseEntity showAllTradersByAscRating() {
        if (myUserRepository.findAllByIsApprovedIsTrue().isPresent()) {
            List<MyUser> myUsers = myUserRepository.findAllByIsApprovedIsTrue().get();
            myUsers.sort(Comparator.comparing(MyUser::getRating, Comparator.reverseOrder()));
            return ResponseEntity.ok(myUsers);
        } else {
            return ResponseEntity.status(404).body("Users not found");
        }

    }

    public ResponseEntity showAllTradersByGame(String name) {
        Long id = gameRepository.findGameByName(name).get().getId();
        if(gameObjectRepository.findAllByGameId(id).isPresent()){
            List<GameObject> gameObjects = gameObjectRepository.findAllByGameId(id).get();
            List<MyUser> myUsers = gameObjects.stream().map(GameObject::getAuthor).distinct().collect(Collectors.toList());
            return ResponseEntity.ok(myUsers);
        }else{
            return ResponseEntity.status(404).body("No one users have been found for this game");
        }

    }


    public void refreshRating(Long id) {
        Double ratingTrader;
        if(commentRepository.findAllByTraderIdAndIsApprovedIsTrue(id).isPresent()){
            ratingTrader = commentRepository.findAllByTraderIdAndIsApprovedIsTrue(id).get().stream().mapToDouble(Comment::getRating)
                    .average().orElse(0);
        }else {
            ratingTrader = 0.0;
        }

        MyUser myUser = myUserRepository.findMyUserByIdAndIsApprovedTrue(id).get();
        myUser.setRating(ratingTrader);
        myUserRepository.save(myUser);

    }
}


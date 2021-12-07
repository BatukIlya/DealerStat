package dealerstat.service;

import dealerstat.dto.GameObjectDto;
import dealerstat.entity.GameObject;
import dealerstat.repository.GameObjectRepository;
import dealerstat.repository.GameRepository;
import dealerstat.config.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameObjectService {

    private final GameObjectRepository gameObjectRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final MyUserService myUserService;

    private final GameRepository gameRepository;


    public ResponseEntity<?> createGameObject(GameObjectDto gameObjectDto, HttpServletRequest request) {
        if (gameRepository.findGameById(gameObjectDto.getGame().getId()).isPresent() &&
                myUserService.findById(jwtTokenProvider.getId(request)) != null) {
            GameObject gameObject = new GameObject();
            gameObject.setName(gameObjectDto.getName());
            gameObject.setAuthor(myUserService.findById(jwtTokenProvider.getId(request)));
            gameObject.setText(gameObjectDto.getText());
            gameObject.setGame(gameRepository.findGameById(gameObjectDto.getGame().getId()).get());
            gameObjectRepository.save(gameObject);
            return ResponseEntity.ok(gameObject);
        } else {
            return ResponseEntity.status(404).body("Game not found");
        }

    }

    public ResponseEntity<?> updateGameObject(GameObjectDto gameObjectDto, Long id, HttpServletRequest request) {

        if (gameObjectRepository.findGameObjectById(id).isPresent()) {
            GameObject gameObject = gameObjectRepository.findGameObjectById(id).get();
            if (jwtTokenProvider.getId(request).equals(gameObject.getAuthor().getId())) {
                gameObject.setName(gameObjectDto.getName());
                gameObject.setText(gameObjectDto.getText());
                return ResponseEntity.ok(gameObject);
            } else {
                return ResponseEntity.status(403).body("Access denied");
            }

        } else {
            return ResponseEntity.status(404).body("Game object not found");
        }

    }

    public ResponseEntity<?> deleteGameObject(Long id, HttpServletRequest request) {

        if (gameObjectRepository.findGameObjectById(id).isPresent()) {
            GameObject gameObject = gameObjectRepository.findGameObjectById(id).get();
            if (jwtTokenProvider.getId(request).equals(gameObject.getAuthor().getId())) {
                gameObjectRepository.deleteById(id);
                return ResponseEntity.ok("Game object is deleted");
            } else {
                return ResponseEntity.status(403).body("Access denied");
            }
        } else {
            return ResponseEntity.status(404).body("Game object not found");
        }


    }

    public List<GameObject> showAll() {
        return gameObjectRepository.findAll();
    }

    public ResponseEntity<?> showMyGameObjects(HttpServletRequest request) {
        Long id = jwtTokenProvider.getId(request);
        if (gameObjectRepository.findAllByAuthorId(id).isPresent()) {
            return ResponseEntity.ok(gameObjectRepository.findAllByAuthorId(id).get());
        } else {
            return ResponseEntity.status(404).body("Game objects not found");
        }
    }
}

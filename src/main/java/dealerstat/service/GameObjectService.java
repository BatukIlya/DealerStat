package dealerstat.service;

import dealerstat.dto.GameObjectDto;
import dealerstat.entity.GameObject;
import dealerstat.repository.GameObjectRepository;
import dealerstat.repository.GameRepository;
import dealerstat.security.jwt.JwtTokenProvider;
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


    public GameObject createGameObject(GameObjectDto gameObjectDto, HttpServletRequest request) {
        GameObject gameObject = new GameObject();
        gameObject.setName(gameObjectDto.getName());
        gameObject.setAuthor(myUserService.findMyUserById(jwtTokenProvider.getId(request)));
        gameObject.setText(gameObjectDto.getText());
        gameObject.setGame(gameRepository.findGameByName(gameObjectDto.getGame().getName()).get());
        return gameObjectRepository.save(gameObject);
    }

    public ResponseEntity updateGameObject(GameObjectDto gameObjectDto, Long id, HttpServletRequest request) {

        if (gameObjectRepository.findGameObjectById(id).isPresent()) {
            GameObject gameObject = gameObjectRepository.findGameObjectById(id).get();
            if (jwtTokenProvider.getId(request).equals(gameObject.getAuthor().getId())) {
                gameObject.setName(gameObjectDto.getName());
                gameObject.setText(gameObjectDto.getText());
                return ResponseEntity.ok("Game object successfully created");
            } else {
                return ResponseEntity.status(403).body("Access denied");
            }

        }else {
            return ResponseEntity.badRequest().body("Game object not found");
        }

    }

    public void deleteGameObject(Long id) {
        gameObjectRepository.deleteById(id);
    }

    public List<GameObject> showAll() {
        return gameObjectRepository.findAll();
    }
}

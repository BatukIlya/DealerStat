package DealerStat.сontroller;

import DealerStat.dto.GameObjectDto;
import DealerStat.entity.GameObject;
import DealerStat.repository.GameObjectRepository;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api
public class GameObjectController {

    private final GameObjectRepository gameObjectRepository;

    @PostMapping("/create_game_object")
    public GameObject createGameObject(GameObjectDto gameObjectDto){
        GameObject gameObject = new GameObject();
        gameObject.setName(gameObjectDto.getName());
        gameObject.setText(gameObjectDto.getText());
        return gameObjectRepository.save(gameObject);
    }
}

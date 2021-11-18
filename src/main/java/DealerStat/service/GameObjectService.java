package DealerStat.service;

import DealerStat.dto.GameObjectDto;
import DealerStat.entity.GameObject;
import DealerStat.repository.GameObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameObjectService {

    private final GameObjectRepository gameObjectRepository;

    public GameObject createGameObject(GameObjectDto gameObjectDto) {
        GameObject gameObject = new GameObject();
        gameObject.setName(gameObjectDto.getName());
        gameObject.setText(gameObject.getText());
        return gameObjectRepository.save(gameObject);
    }
}

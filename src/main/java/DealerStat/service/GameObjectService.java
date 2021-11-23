package DealerStat.service;

import DealerStat.dto.GameObjectDto;
import DealerStat.entity.GameObject;
import DealerStat.repository.GameObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameObjectService {

    private final GameObjectRepository gameObjectRepository;

    public GameObject createGameObject(GameObjectDto gameObjectDto) {
        GameObject gameObject = new GameObject();
        gameObject.setName(gameObjectDto.getName());
        gameObject.setText(gameObjectDto.getText());
        return gameObjectRepository.save(gameObject);
    }

    public GameObject updateGameObject(GameObjectDto gameObjectDto, Long id){
        GameObject gameObject  = gameObjectRepository.findGameObjectById(id);
        gameObject.setName(gameObjectDto.getName());
        gameObject.setText(gameObjectDto.getText());
        return gameObjectRepository.save(gameObject);
    }

    public void deleteGameObject(Long id){
        gameObjectRepository.deleteById(id);
    }

    public List<GameObject> showAll(){
        return gameObjectRepository.findAll();
    }
}

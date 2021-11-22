package DealerStat.сontroller;

import DealerStat.dto.GameObjectDto;
import DealerStat.entity.GameObject;
import DealerStat.service.GameObjectService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api
public class GameObjectController {

    private final GameObjectService gameObjectService;

    @PostMapping("/object")
    public GameObject createGameObject(GameObjectDto gameObjectDto){
        return gameObjectService.createGameObject(gameObjectDto);
    }

    @PutMapping("/{gameObjectId}/updateGameObject")
  public GameObject updateGameObject(GameObjectDto gameObjectDto, @PathVariable Long gameObjectId){
        return gameObjectService.updateGameObject(gameObjectDto, gameObjectId);
    }

    @GetMapping("/object")
    public List<GameObject> showAllGameObject(){
        return gameObjectService.showAll();
    }

    @DeleteMapping("/object/{gameObjectId}")
    public void deleteGameObject(@PathVariable Long gameObjectId){
        gameObjectService.deleteGameObject(gameObjectId);
    }
}

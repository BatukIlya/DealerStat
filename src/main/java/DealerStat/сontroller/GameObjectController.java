package DealerStat.сontroller;

import DealerStat.dto.GameObjectDto;
import DealerStat.entity.GameObject;
import DealerStat.service.GameObjectService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api
public class GameObjectController {

    private final GameObjectService gameObjectService;

//    @PreAuthorize("hasAnyAuthority('ADMIN', 'TRADER')")
    @PostMapping("/object")
    public GameObject createGameObject(@RequestBody GameObjectDto gameObjectDto, HttpServletRequest request){
        return gameObjectService.createGameObject(gameObjectDto, request);
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN', 'TRADER')")
    @PutMapping("/{gameObjectId}/updateGameObject")
  public ResponseEntity updateGameObject(@RequestBody GameObjectDto gameObjectDto, @PathVariable Long gameObjectId){
        return gameObjectService.updateGameObject(gameObjectDto, gameObjectId);
    }

    @GetMapping("/object")
    public List<GameObject> showAllGameObject(){
        return gameObjectService.showAll();
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN','TRADER')")
    @DeleteMapping("/object/{gameObjectId}")
    public void deleteGameObject(@PathVariable Long gameObjectId){
        gameObjectService.deleteGameObject(gameObjectId);
    }
}

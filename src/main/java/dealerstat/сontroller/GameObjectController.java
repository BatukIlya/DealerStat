package dealerstat.сontroller;

import dealerstat.dto.GameObjectDto;
import dealerstat.entity.GameObject;
import dealerstat.service.GameObjectService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api
public class GameObjectController {

    private final GameObjectService gameObjectService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TRADER')")
    @PostMapping("/object")
    public ResponseEntity<?> createGameObject(@RequestBody @Valid GameObjectDto gameObjectDto, HttpServletRequest request) {
        return gameObjectService.createGameObject(gameObjectDto, request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TRADER')")
    @PutMapping("/{gameObjectId}/updateGameObject")
    public ResponseEntity<?> updateGameObject(@RequestBody GameObjectDto gameObjectDto, @PathVariable Long gameObjectId,
                                              HttpServletRequest request) {
        return gameObjectService.updateGameObject(gameObjectDto, gameObjectId, request);
    }

    @GetMapping("/object")
    public List<GameObject> showAllGameObject() {
        return gameObjectService.showAll();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','TRADER')")
    @DeleteMapping("/object/{gameObjectId}")
    public ResponseEntity<?> deleteGameObject(@PathVariable Long gameObjectId, HttpServletRequest request) {
        return gameObjectService.deleteGameObject(gameObjectId, request);
    }
}

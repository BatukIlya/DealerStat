package dealerstat.dto;

import dealerstat.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameObjectDto {

    @NotNull
    private String name;

    @NotNull
    @Max(255)
    private String text;

    @NotNull
    private Game game;

}

package dealerstat.dto;

import dealerstat.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameObjectDto {

    @NotBlank
    private String name;

    @NotBlank
    @Size(max = 255)
    private String text;

    @NotEmpty
    private Game game;

}

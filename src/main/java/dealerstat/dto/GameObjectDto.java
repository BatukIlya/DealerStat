package dealerstat.dto;

import dealerstat.entity.Game;
//import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameObjectDto {

    @NotBlank
    private String name;

    @NotBlank
    @Size(max = 255)
    private String text;


    private Game game;

}

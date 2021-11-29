package DealerStat.dto;

import DealerStat.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameObjectDto {

    private String name;

    private String text;

    private GameDto game;

}

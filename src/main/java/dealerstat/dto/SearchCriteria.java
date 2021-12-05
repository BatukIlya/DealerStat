package dealerstat.dto;

import dealerstat.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
    private Integer count;

    private boolean sortByAsc;

    private Game game;


}



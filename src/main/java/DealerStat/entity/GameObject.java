package DealerStat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String text;

    @ManyToOne
    private MyUser author;

    private LocalDate createdAt = LocalDate.now();

    private LocalDate updatedAt;

    @ManyToOne
    private Game game;

}

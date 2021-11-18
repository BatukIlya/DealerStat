package DealerStat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    private MyUser author;

    @ManyToOne(fetch = FetchType.LAZY)
    private MyUser trader;

    private LocalDate createdAt = LocalDate.now();

    private boolean isApproved = false;

}

package DealerStat.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.EAGER)
    private MyUser trader;


    private LocalDateTime createdAt = LocalDateTime.now();


    private LocalDateTime updatedAt;

    private boolean isApproved = false;

    private Double rating;

}

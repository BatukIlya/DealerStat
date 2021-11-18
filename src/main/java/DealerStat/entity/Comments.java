package DealerStat.entity;

import javax.persistence.*;

@Entity
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    private MyUser author;

    @ManyToOne
    private MyUser trader;

    private boolean isApproved;


}

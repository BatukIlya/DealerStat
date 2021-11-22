package DealerStat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String firstName;

    private String lastName;

    private String password;


    private String email;

    private LocalDate createdAt = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    private boolean isApproved = false;


}

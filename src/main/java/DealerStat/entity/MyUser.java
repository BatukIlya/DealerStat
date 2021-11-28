package DealerStat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUser{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @JsonIgnore
    private String password;

    private String email;

    @CreationTimestamp
    private Date createAt;

    @JsonIgnore
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "my_user_role", joinColumns = @JoinColumn(name = "my_user_id"))
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    @JsonIgnore
    private boolean isApproved = false;

    @JsonIgnore
    private boolean isApprovedEmail = true;

    @Column(nullable = false)
    private Double rating = 0.0;

}

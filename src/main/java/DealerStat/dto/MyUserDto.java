package DealerStat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUserDto {

    private String firstName;

    private String lastName;

    private String password;

    private String email;

}

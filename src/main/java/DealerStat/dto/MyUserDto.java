package DealerStat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyUserDto {

    private String firstName;

    private String lastName;

    private String password;

    private String email;

}

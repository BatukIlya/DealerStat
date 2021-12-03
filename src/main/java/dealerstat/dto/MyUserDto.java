package dealerstat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUserDto {

    @NotNull
    @Max(100)
    private String firstName;

    @NotNull
    @Max(100)
    private String lastName;

    @NotNull
    @Min(8)
    private String password;

    @NotNull
    @Email
    private String email;

}

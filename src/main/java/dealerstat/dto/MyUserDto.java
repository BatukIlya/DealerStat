package dealerstat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUserDto {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Min(8)
    private String password;

    @NotNull
    @Email
    private String email;

}

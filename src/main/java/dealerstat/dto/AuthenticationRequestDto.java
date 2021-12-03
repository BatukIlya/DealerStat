package dealerstat.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AuthenticationRequestDto {

    @NotNull
    @Email
    private String email;

    @NotNull
    @Min(8)
    private String password;
}
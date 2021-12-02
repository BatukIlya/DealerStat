package dealerstat.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AuthenticationRequestDto {
    @NotNull
    private String email;
    @NotNull
    @Min(8)
    private String password;
}
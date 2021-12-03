package dealerstat.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class AuthenticationRequestDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
package dealerstat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
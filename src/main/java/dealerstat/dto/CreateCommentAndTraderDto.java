package dealerstat.dto;

import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentAndTraderDto {


    @Size(max = 100)
    private String firstNameTrader;


    @Size(max = 100)
    private String lastNameTrader;

    @NotBlank
    @Email
    @Size(max = 255)
    private String emailTrader;

    @NotBlank
    @Size(max = 500)
    private String messageComment;

    @DecimalMin(value = "0.00001")
    @DecimalMax(value = "5.0")
    private Double ratingComment;

}

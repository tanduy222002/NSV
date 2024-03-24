package nsv.com.nsvserver.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import nsv.com.nsvserver.Anotation.StrongPassword;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AuthRequestDto {
    @NotBlank(message = "user name is mandatory")
    @Schema( example = "tanduy222002@gmail.com", required = true)
    private String username;
    @NotBlank(message = "password is mandatory")
    @Schema( example = "Tdt@01263655736", required = true)
    @StrongPassword
    private String password;

}

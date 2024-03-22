package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import nsv.com.nsvserver.Anotation.MatchingPassword;
import nsv.com.nsvserver.Anotation.StrongPassword;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@MatchingPassword(
        password = "password",
        confirmPassword = "confirmPassword"
)
public class SignUpRequest {
    @Schema( example = "tanduy222002@gmail.com", required = true)
    @NotBlank(message = "user name is mandatory")
    @Size(max = 50,message = "user name must not exceed 50 characters")
    @JsonProperty("user_name")
    private String userName;

    @NotBlank(message = "password is mandatory")
    @StrongPassword
    @Schema( example = "Tdt@01263655736", required = true)
    private String password;

    @StrongPassword(message = "Confirm password must be 8 characters long and combination of uppercase letters, lowercase letters, numbers, special characters.")
    @Schema( example = "Tdt@01263655736", required = true)
    @JsonProperty("confirm_password")
    private String confirmPassword;

    @Schema(example = "[\"ROLE_EMPLOYEE\", \"ROLE_MANAGER\"]", required = false)
    private List<String> roles;
}

package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsv.com.nsvserver.Annotation.MatchingPassword;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MatchingPassword(
        password = "newPassword",
        confirmPassword = "confirmNewPassword"
)
public class ResetPasswordRequestDto {
    @NotBlank(message = "Please enter new password")
    @JsonProperty("new_password")
    private String newPassword;
    @JsonProperty("confirm_new_password")
    @NotBlank(message ="Please confirm new password")
    private String confirmNewPassword;

    @NotBlank(message = "Otp code must not be blank")
    private String code;
    @Schema( example = "tanduy222002@gmail.com", required = true,description = "The identifier that used to get otp code")
    @NotBlank(message = "Identifier that used to get otp must not be blank")
    private String identifier;
}

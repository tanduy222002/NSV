package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsv.com.nsvserver.Anotation.MatchingPassword;

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
}

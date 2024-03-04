package nsv.com.nsvserver.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpVerifyRequestDto {
    @NotBlank(message = "Otp code must not be blank")
    private String code;
    private String identifier;
}

package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateOtpRequestDto {
    @NotBlank(message = "Identifier for otp is required")
    @Schema( example = "tanduy222002@gmail.com", required = true)
    private String identifier;
    @Schema( example = "email", required = false)
    @JsonProperty("delivery_method")
    private String deliveryMethod ="email";
}

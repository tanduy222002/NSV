package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateOtpResponseDto {
    @JsonProperty("otp_expiry_date")
    private Date otpExpiryDate;
    private String message;
}

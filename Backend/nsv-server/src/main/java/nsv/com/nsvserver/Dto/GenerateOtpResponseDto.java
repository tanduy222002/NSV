package nsv.com.nsvserver.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsv.com.nsvserver.Entity.Otp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateOtpResponseDto {
    private Date otp_expiry_date;
    private String message;
}

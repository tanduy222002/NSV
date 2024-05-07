package nsv.com.nsvserver.Dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerProfileDto {
    private String name;
    private String phone;
    private String email;
    private AddressDetailDto address;
}

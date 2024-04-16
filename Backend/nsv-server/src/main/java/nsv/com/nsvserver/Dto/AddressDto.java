package nsv.com.nsvserver.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private String address;
    private Integer wardId;
    private Integer districtId;
    private Integer provinceId;
}

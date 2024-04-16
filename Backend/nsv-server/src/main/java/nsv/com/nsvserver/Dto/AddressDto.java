package nsv.com.nsvserver.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    @NotBlank(message = "address is mandatory")
    private String address;
    @NotNull(message = "wardId is mandatory")
    private Integer wardId;
    @NotNull(message = "districtId is mandatory")
    private Integer districtId;
    @NotNull(message = "provinceId is mandatory")
    private Integer provinceId;
}

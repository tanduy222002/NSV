package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsv.com.nsvserver.Entity.Address;
import nsv.com.nsvserver.Util.ConvertUtil;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDetailDto {
    @JsonProperty("address_string")
    private String addressString;
    private Address address;


    public AddressDetailDto(Address address) {
        this.address = address;
        this.addressString = ConvertUtil.convertAddressToString(address);
    }
}

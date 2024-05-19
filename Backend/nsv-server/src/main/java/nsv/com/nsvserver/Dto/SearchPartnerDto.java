package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsv.com.nsvserver.Entity.Address;
import nsv.com.nsvserver.Entity.Partner;
import nsv.com.nsvserver.Util.ConvertUtil;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchPartnerDto {
    private Integer ID;
    private String name;
    private String phone;

    @Schema(hidden = true)
    private Address address;

    @JsonProperty("address_string")
    private String addressString;

    public SearchPartnerDto(Integer ID, String name, String phone) {
        this.ID = ID;
        this.name = name;
        this.phone = phone;
    }

    public SearchPartnerDto(Integer ID, String name, String phone, Address address) {
        this.ID = ID;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.addressString = ConvertUtil.convertAddressToString(address);
    }
}

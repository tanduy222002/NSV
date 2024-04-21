package nsv.com.nsvserver.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsv.com.nsvserver.Entity.Address;
import nsv.com.nsvserver.Entity.Partner;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchPartnerDto {
    private Integer ID;
    private String name;
    private String phone;

    @Schema(hidden = true)
    private Address address;

    public SearchPartnerDto(Integer ID, String name, String phone) {
        this.ID = ID;
        this.name = name;
        this.phone = phone;
    }
}

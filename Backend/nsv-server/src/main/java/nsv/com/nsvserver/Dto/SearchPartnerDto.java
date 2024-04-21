package nsv.com.nsvserver.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsv.com.nsvserver.Entity.Partner;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchPartnerDto {
    private Integer ID;
    private String name;
    private String phone;

}

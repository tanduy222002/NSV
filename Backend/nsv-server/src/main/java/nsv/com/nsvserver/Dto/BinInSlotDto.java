package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BinInSlotDto {

    @JsonProperty("bin_id")
    private Integer id;
    @JsonProperty("ticket_name")
    private String ticketName;

    @JsonProperty("import_date")
    private Date importDate;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("product_type")
    private String productType;

    private String packaged;

    private String weight;

    @JsonProperty("in_slot_weight")
    private String inSlotWeight;

}

package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportBinInSlot {
    @JsonProperty("slot_id")
    private Integer slotId;
    @JsonProperty("slot_name")
    private String slotName;
    @JsonProperty("warehouse_name")
    private String warehouseName;
    private String location;
    @JsonProperty("in_slot_weight")
    private Double inSlotWeight;
    @JsonProperty("taken_weight")
    private Double takenWeight =0.0;
    @JsonProperty("taken_area")
    private Double takenArea =0.0;
    private BinDto bin;
    @JsonProperty("warehouse_id")
    private Integer warehouseId;


}

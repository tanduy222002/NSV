package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BinWithSlotDto {
    @JsonProperty("slot_id")
    private Integer slotId;
    private double weight;
    @JsonProperty("area")
    private double area;

}

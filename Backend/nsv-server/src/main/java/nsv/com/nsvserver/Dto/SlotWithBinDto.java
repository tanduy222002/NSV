package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlotWithBinDto {
    @JsonProperty("slot_id")
    private Integer slotId;
    @JsonProperty("place_taken")
    private Double takenPlace;
}

package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsv.com.nsvserver.Entity.Slot;
import nsv.com.nsvserver.Util.ConvertUtil;
import nsv.com.nsvserver.Util.NumberUtil;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlotStatisticInWarehouseDto {
    @JsonProperty("slot_id")
    private Integer slotId;
    private String name;
    private String containing;
    private String capacity;
    private List<StatisticOfProductInWarehouseDto> products;

    public SlotStatisticInWarehouseDto(Slot slot, List<StatisticOfProductInWarehouseDto> products) {
        this.slotId= slot.getId();
        this.name=slot.getName();
        this.containing= NumberUtil.removeTrailingZero(slot.getContaining());
        this.capacity= NumberUtil.removeTrailingZero(slot.getCapacity());
        this.products= products;
    }
}

package nsv.com.nsvserver.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WarehouseDetailDto {
    private Integer id;
    private String name;
    private Double capacity;
    private Double containing;
    private MapInWareHouseDto map;
}

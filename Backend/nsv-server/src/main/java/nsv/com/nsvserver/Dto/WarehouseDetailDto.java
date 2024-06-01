package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(value = "lower_temperature")
    private Double lowerTemperature;
    @JsonProperty(value = "upper_temperature")
    private Double upperTemperature;
}

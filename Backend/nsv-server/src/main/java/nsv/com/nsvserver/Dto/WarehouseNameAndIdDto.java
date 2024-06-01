package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseNameAndIdDto {
    private Integer id;
    private String name;
    @JsonProperty(value = "lower_temperature")
    private Double lowerTemperature;

    @JsonProperty(value = "upper_temperature")
    private Double upperTemperature;

    public WarehouseNameAndIdDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}

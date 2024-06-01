package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeDto {
    private Integer id;
    private String name;
    @JsonProperty(value = "lower_temperature")
    private String lowerTemperature;
    @JsonProperty(value = "upper_temperature")
    private String upperTemperature;

    public TypeDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}

package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QualityInTypeDto {
    @JsonProperty("quality_id")
    private Integer qualityId;
    @JsonProperty("type_id")
    private Integer typeId;
    private String name;
    @JsonProperty(value = "lower_temperature")
    private String lowerTemperature;
    @JsonProperty(value = "upper_temperature")
    private String upperTemperature;

    public QualityInTypeDto(Integer qualityId, String name) {
        this.qualityId = qualityId;
        this.name = name;
    }
}

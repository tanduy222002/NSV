package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticOfTypeAndQuality {
    private String name;
    @JsonProperty("type_img")
    private String typeImg;
    private Double weight;


}

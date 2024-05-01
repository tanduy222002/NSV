package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BinDto {
    private Integer id;
    private String product;
    @JsonProperty("product_img")
    private String productImg;
    @JsonProperty("quality_id")
    private Integer qualityId;
    @JsonProperty("quality_with_type")
    private String qualityWithType;
    private Double weight;
    private String packaged;

}

package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBinDto {
    @JsonProperty("quality_id")
    private String qualityId;
    @JsonProperty("package_type")
    private String packageType;
    private Integer count;
    private Double weight;
    @JsonProperty("slot_id")
    private String slotId;
}

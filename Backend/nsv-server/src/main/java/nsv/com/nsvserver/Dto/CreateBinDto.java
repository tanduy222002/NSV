package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBinDto {
    @JsonProperty("quality_id")
    private Integer qualityId;
    @JsonProperty("package_type")
    private String packageType;
    @NotNull
    private double weight;

    @NotNull
    private List<BinWithSlotDto> binWithSlot;

    @NotNull(message = "price of product is mandatory")
    private double price;
    private String note;
}

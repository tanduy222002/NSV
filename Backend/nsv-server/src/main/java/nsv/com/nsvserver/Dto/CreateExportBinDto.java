package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateExportBinDto {
    @JsonProperty("quality_id")
    private Integer qualityId;
    @JsonProperty("package_type")
    private String packageType;
    @NotNull
    private double weight;

    @NotNull
    @JsonProperty("import_bin_with_slot")
    private List<ImportBinInSlot> importBinWithSlot;

    @NotNull(message = "price of product is mandatory")
    private double price;
    private String note;
}

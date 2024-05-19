package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeQualityDto {
    @JsonProperty("product")
    @Valid
    private ProductCreateDto productCreateDto;
    @JsonProperty("types")
    @Valid
    private List<TypeWithQualityDto> typeWithQualityListDto;
}

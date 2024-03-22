package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class TypeCreateDto {

    @NotBlank(message = "name is mandatory")
    @Schema( example = "Ri 6", required = true)
    private String name;

    @Schema( example = "", required = false)
    private String seasonal;

    @Schema( example = "-20", required = false)
    @JsonProperty("lower_temperature_threshold")
    private String lowerTemperatureThreshold;

    @JsonProperty("upper_temperature_threshold")
    @Schema( example = "15", required = false)
    private String upperTemperatureThreshold;

    @Schema(example = "https://cdn.tgdd.vn/2021/05/content/thumb2(3)-800x500-2.jpg")
    private String image;

}

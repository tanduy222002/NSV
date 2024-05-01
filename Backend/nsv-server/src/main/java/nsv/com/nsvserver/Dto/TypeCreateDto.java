package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import nsv.com.nsvserver.Annotation.Base64Img;


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

    @Base64Img
    @Schema(description = "The base64 encoded of image start with prefix: \"data:image/jpeg;base64,\" + code")
    private String image;

}

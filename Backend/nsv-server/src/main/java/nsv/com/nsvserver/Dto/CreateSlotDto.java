package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSlotDto {

    private String name;

    @NotNull(message = "capacity is mandatory")
    private Double capacity;
    @Schema(hidden = true)
    @JsonIgnore
    private Double containing=0.0;
    @Schema(hidden = true)
    @JsonIgnore
    private String status="Chưa chứa";

    private String description;

    @JsonProperty("x_position")
    private Integer xPosition;


}

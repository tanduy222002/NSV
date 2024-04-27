package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotInRowDto {

    private String name;

    private Double capacity;

    private String status="EMPTY";

    private String description;

    @JsonProperty("x_position")
    private Integer xPosition;


}

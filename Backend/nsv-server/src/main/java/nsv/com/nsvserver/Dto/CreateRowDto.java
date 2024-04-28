package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRowDto {
    @NotNull(message = "row name is mandatory")
    private String name;
    @NotNull(message = "y_position is mandatory")
    @JsonProperty("y_position")
    private int yPosition;
    @Valid
    private List<CreateSlotDto> slotDtos;

}

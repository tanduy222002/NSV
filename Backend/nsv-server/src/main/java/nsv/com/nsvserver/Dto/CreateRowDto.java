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
    private String name;
    @JsonProperty("y_position")
    private int yPosition;
    @NotNull(message = "capacity is mandatory")
    private Double capacity;
    @Valid
    private List<CreateSlotDto> slotDtos;

}
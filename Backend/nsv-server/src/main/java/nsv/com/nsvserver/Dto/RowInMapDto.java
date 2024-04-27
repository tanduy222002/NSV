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
public class RowInMapDto {
    private String name;
    @JsonProperty("y_position")
    private int yPosition;
    @Valid
    private List<SlotInRowDto> slots;

}

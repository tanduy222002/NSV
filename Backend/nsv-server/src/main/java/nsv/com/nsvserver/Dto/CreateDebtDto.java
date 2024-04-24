package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDebtDto {
    private String name;
    private Double value;
    @JsonProperty("due_date")
    private Date dueDate;
    private String description;
    @Schema(example = "VND")
    private String unit;
}

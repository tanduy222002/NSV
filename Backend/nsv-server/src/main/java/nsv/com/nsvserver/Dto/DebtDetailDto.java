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
public class DebtDetailDto {
    private String name;

    private Double value;

    @JsonProperty("create_date")
    private Date create_date;
    @JsonProperty("due_date")
    private Date dueDate;
    @JsonProperty("is_paid")
    private Boolean isPaid;
    private String description;
    private String unit="VND";
}

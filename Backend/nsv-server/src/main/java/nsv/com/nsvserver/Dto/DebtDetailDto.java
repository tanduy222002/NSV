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
    @Schema(hidden = true)
    private Integer id;
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
    @JsonProperty("paid_date")
    private Date paidDate;


    public DebtDetailDto(Integer id, String name, Double value, Date create_date, Date dueDate, Boolean isPaid, String description, String unit) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.create_date = create_date;
        this.dueDate = dueDate;
        this.isPaid = isPaid;
        this.description = description;
        this.unit = unit;
    }
}

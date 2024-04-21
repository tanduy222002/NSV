package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransferTicketDto {
    private String name;
    @JsonProperty("provider_id")
    private Integer providerId;
    private String addressId;
    @JsonProperty("import_date")
    private Date importDate;
}

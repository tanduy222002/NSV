package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransferTicketDto {
    private String name;
    @JsonProperty("provider_id")
    private Integer providerId;
    @JsonProperty("import_date")
    private Date importDate;

    private Double weight;

    private String transporter;

    private Double value;

    private CreateDebtDto debtDto;

    private List<CreateBinDto> binDto;


}

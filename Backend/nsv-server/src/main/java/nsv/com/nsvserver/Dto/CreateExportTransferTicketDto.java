package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateExportTransferTicketDto {
    private String name;
    @JsonProperty("customer_id")
    private Integer customerId;
    @JsonProperty("export_date")
    private Date exportDate;

//    @JsonProperty("transport_date")
//    private Date transportDate;

//    private Double weight;

    private String transporter;

    private String description;

//    private Double value;

    private CreateDebtDto debtDto;

    private List<CreateExportBinDto> exportBinDto;


}

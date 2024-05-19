package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateExportTransferTicketDto {
    @NotBlank(message = "name is mandatory")
    private String name;
    @JsonProperty("customer_id")
    @NotNull(message = "Customer id is mandatory")
    private Integer customerId;
    @JsonProperty("export_date")
    private Date exportDate;

//    @JsonProperty("transport_date")
//    private Date transportDate;

//    private Double weight;

    private String transporter = "Không có";

    private String description;

//    private Double value;
    @Valid
    private CreateDebtDto debtDto;
    @Valid
    private List<CreateExportBinDto> exportBinDto;


}

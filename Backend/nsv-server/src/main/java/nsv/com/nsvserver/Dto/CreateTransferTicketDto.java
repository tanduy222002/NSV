package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CreateTransferTicketDto {
    @NotBlank(message = "name is mandatory")
    private String name;

    @NotNull(message = "provider_id must not be null")
    @JsonProperty("provider_id")
    private Integer providerId;

    @JsonProperty("import_date")
    private Date importDate;

//    @JsonProperty("transport_date")
//    private Date transportDate;

//    private Double weight;

    private String transporter ="Không có";

    private String description;

//    private Double value;

    private CreateDebtDto debtDto;

    private List<CreateBinDto> binDto;


}

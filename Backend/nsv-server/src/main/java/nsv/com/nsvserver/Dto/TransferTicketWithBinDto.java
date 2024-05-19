package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsv.com.nsvserver.Dto.BinDto;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferTicketWithBinDto {
    private Integer id;
    private String name;
    @JsonProperty("import_date")
    private Date importDate;
    private String description;
    private double weight;
    private String status;
    List<BinDto> bins;
}

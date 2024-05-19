package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportBinWithImportBin {
    @JsonProperty("import_bin_id")
    private Integer importBinId;

    @JsonProperty("import_ticket_name")
    private String importTicketName;

    @JsonProperty("import_ticket_id")
    private Integer importTicketId;

    @JsonProperty("taken_weight")
    private Double takenWeight =0.0;

    @JsonProperty("taken_area")
    private Double takenArea =0.0;

    private BinDto bin;


}

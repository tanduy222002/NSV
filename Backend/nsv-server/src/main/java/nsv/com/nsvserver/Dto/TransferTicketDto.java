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
public class TransferTicketDto {
    private Integer id;
    private String name;
    @JsonProperty("transfer_date")
    private Date transferDate;
    private String description;
    private double weight;
    private String product;
    @JsonProperty("number_of_products")
    private Integer numberOfProducts;
    private String status;
}

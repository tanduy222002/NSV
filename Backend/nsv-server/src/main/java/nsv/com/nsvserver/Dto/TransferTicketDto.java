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
    private String type;
    private Double value;

    public TransferTicketDto(Integer id, String name, Date transferDate, String description, double weight, String product, Integer numberOfProducts, String status) {
        this.id = id;
        this.name = name;
        this.transferDate = transferDate;
        this.description = description;
        this.weight = weight;
        this.product = product;
        this.numberOfProducts = numberOfProducts;
        this.status = status;
    }

    public TransferTicketDto(Integer id, String name, Date transferDate, String description, double weight, long numberOfProducts, String status, String type, Double value) {
        this.id = id;
        this.name = name;
        this.transferDate = transferDate;
        this.description = description;
        this.weight = weight;
        this.numberOfProducts = Math.toIntExact(numberOfProducts);
        this.status = status;
        this.type = type;
        this.value = value;
    }
}

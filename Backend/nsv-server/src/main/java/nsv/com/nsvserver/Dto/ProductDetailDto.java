package nsv.com.nsvserver.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDetailDto {
    private Integer id;
    private String name;
    private String image;
    private double inventory=0.0;
    @JsonProperty("number_of_containing_slot")
    private Long numberOfContainingSlot=0l;

    public ProductDetailDto(Integer id, String name, String image, Double inventory, Long numberOfContainingSlot) {
        this.id = id;
        this.name = name;
        this.image = image;
        if(inventory!=null){
            this.inventory = inventory;
        }
        if(numberOfContainingSlot!=null){
            this.numberOfContainingSlot = numberOfContainingSlot;
        }

    }
}

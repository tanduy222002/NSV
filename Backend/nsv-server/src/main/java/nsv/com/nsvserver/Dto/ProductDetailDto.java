package nsv.com.nsvserver.Dto;

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

    public ProductDetailDto(Integer id, String name, String image, Double inventory) {
        this.id = id;
        this.name = name;
        this.image = image;
        if(inventory!=null){
            this.inventory = inventory;
        }

    }
}

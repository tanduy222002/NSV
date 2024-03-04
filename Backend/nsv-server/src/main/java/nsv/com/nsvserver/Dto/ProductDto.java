package nsv.com.nsvserver.Dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nsv.com.nsvserver.Entity.Product;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter

public class ProductDto {

    private Integer id;


    private String name;


    private String variety;

    private String image;


    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.image=product.getImage();
        this.variety=product.getVariety();

    }
}

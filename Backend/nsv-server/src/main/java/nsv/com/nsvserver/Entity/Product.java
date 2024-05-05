package nsv.com.nsvserver.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nsv.com.nsvserver.Dto.ProductCreateDto;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name",unique = true)
    private String name;

    @Column(name = "variety")
    private String variety;
    @Column(name = "image")
    private String image;

    @OneToMany(fetch = FetchType.LAZY,mappedBy="product",cascade = CascadeType.ALL)
//    @JsonManagedReference
    private List<Type> types;

    public Product() {
    }

    public void addType(Type type) {
        this.types.add(type);
    }

    public Product(ProductCreateDto dto) {
        this.name=dto.getName();
        this.image=dto.getImage();
        this.variety=dto.getVariety();
        this.types =new ArrayList<>();
    }
}

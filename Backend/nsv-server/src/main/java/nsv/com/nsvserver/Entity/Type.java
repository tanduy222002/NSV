package nsv.com.nsvserver.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nsv.com.nsvserver.Dto.TypeCreateDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "Type")
public class Type {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "seasonal")
    private String seasonal;

    @Column(name = "lower_temperature_threshold")
    private String lowerTemperatureThreshold;

    @Column(name = "upper_temperature_threshold")
    private String upperTemperatureThreshold;

    @Column(name = "image")
    private String image;

    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

    @OneToMany(mappedBy="type",cascade = CascadeType.ALL)
    private List<Quality> qualities = new ArrayList<>();

    public Type() {
    }
    public void addProduct(Product product) {
        this.product=product;
    }

    public Type(TypeCreateDto dto) {
        this.name=dto.getName();
        this.seasonal=dto.getSeasonal();
        this.image=dto.getImage();
        this.lowerTemperatureThreshold=dto.getLowerTemperatureThreshold();
        this.upperTemperatureThreshold=dto.getUpperTemperatureThreshold();
    }
}

package nsv.com.nsvserver.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Warehouse_Map")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Map {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;


    @JsonIgnore
    @OneToMany(mappedBy="map",cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Warehouse> warehouses;


    @OrderBy("yPosition ASC")
    @JsonManagedReference
    @OneToMany(mappedBy="map",cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Row> row;

    public Map(String name, List<Row> row) {
        System.out.println("construct map");
        this.name = name;
        this.row = row;
    }
}
package nsv.com.nsvserver.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "province")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Province {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy="province",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<District> districts;

    public Province(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}

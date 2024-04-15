package nsv.com.nsvserver.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name="district")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class District {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy="district",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Ward> wards;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="province_id", nullable=false)
    @JsonBackReference
    private Province province;

    public District(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}

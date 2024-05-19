package nsv.com.nsvserver.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;
@Entity
@Table(name = "ward")
@Getter
@Setter
@ToString
public class Ward {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy="ward",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Address> addresses;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="district_id", nullable=false)
    @JsonManagedReference
    private District district;



    public Ward() {
    }

    public Ward(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}

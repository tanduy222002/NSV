package nsv.com.nsvserver.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "Ward")
@Getter
@Setter
@ToString
public class Ward {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(mappedBy="ward",cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<Address>();

    public Ward() {
    }
}

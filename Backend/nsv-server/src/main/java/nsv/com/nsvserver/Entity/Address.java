package nsv.com.nsvserver.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nsv.com.nsvserver.Dto.AddressDto;

@Entity
@Table(name = "Address")
@Getter
@Setter
@ToString

public class Address {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;

    @OneToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="profile_id", nullable=false)
    @JsonIgnore
    private Profile profile;

    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @JsonManagedReference
    @JoinColumn(name="ward_id", nullable=false)
    private Ward ward;


    public Address() {
    }
    public Address(AddressDto dto){

    }

    public Address(String name) {
        this.name = name;
    }
}

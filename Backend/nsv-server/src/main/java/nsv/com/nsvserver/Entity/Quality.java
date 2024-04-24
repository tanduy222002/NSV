package nsv.com.nsvserver.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nsv.com.nsvserver.Dto.QualityCreateDto;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "Quality")
public class Quality {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "note")
    private String description;

    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name="type_id", nullable=false)
    @JsonBackReference
    private Type type;

    @OneToMany(cascade={CascadeType.MERGE, CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
    private List<Bin> bin;
    public Quality(QualityCreateDto dto) {
        this.name=dto.getName();
        this.description=dto.getDescription();
    }

    public Quality() {
    }
}

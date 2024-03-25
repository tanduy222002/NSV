package nsv.com.nsvserver.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nsv.com.nsvserver.Dto.QualityCreateDto;

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

    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="type_id", nullable=false)
    private Type type;

    public Quality(QualityCreateDto dto) {
        this.name=dto.getName();
        this.description=dto.getDescription();
    }

    public Quality() {
    }
}

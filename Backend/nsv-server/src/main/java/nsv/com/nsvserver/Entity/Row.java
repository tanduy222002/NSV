package nsv.com.nsvserver.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Map_Row",uniqueConstraints = {@UniqueConstraint(columnNames = {"y_position", "map_id"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Row {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Integer id;

    @NotNull(message = "y position is mandatory")
    @Column(name = "y_position", nullable = false)
    @JsonProperty("y_position")
    private Integer yPosition;

    @Valid
    @OrderBy("xPosition ASC")
    @OneToMany(mappedBy="row",cascade = CascadeType.ALL)
    private List<Slot> slots;

    @Schema(hidden = true)
    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name="map_id", nullable=false)
    private Map map;

    public Row(Integer yPosition, List<Slot> slots) {
        System.out.println("construct row");
        this.yPosition = yPosition;
        this.slots = slots;
    }


}

package nsv.com.nsvserver.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "Slot",uniqueConstraints = {@UniqueConstraint(columnNames = {"x_position", "row_id"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Slot {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;




    @NotNull(message = "capacity is mandatory")
    @Column(name = "capacity",nullable = false)
    private Double capacity = 0.0;

    @Schema(hidden = true)
    @NotNull(message = "containing is mandatory")
    @Column(name = "containing",nullable = false)
    private Double containing=0.0;

    @Schema(hidden = true)
    @Column(name = "status", nullable = false)
    private String status="EMPTY";

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "x_position", nullable = false)
    @JsonProperty("x_position")
    private Integer xPosition;

    @Schema(hidden = true)
    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name="row_id", nullable=false)
    @JsonBackReference
    private Row row;


    @ManyToMany(mappedBy = "slots",cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private List<Bin> bins;


    public Slot(String name, String description, Integer xPosition) {
        System.out.println("construct slot");
        this.name = name;
        this.description = description;
        this.xPosition = xPosition;
    }

    public void addBin(Bin bin) {
        if (this.bins == null){
            this.bins = new ArrayList<Bin>();
            this.bins.add(bin);
        }
        else{
            this.bins.add(bin);
        }
    }


}

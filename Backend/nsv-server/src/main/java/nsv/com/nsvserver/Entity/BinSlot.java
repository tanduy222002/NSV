package nsv.com.nsvserver.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Bin_Slot")
@Data
@NoArgsConstructor
public class BinSlot {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "area")
    private Double area;


    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "bin_id",referencedColumnName = "id")
    private Bin bin;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "slot_id",referencedColumnName = "id")
    private Slot slot;
}

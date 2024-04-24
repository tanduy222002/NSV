package nsv.com.nsvserver.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bin {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @Column(name = "name")
//    private String name;

    @Column(name = "weight")
    private Double weight=0.0;

    @Column(name = "left_weight")
    private Double leftWeight=0.0;

    @Column(name = "package_type")
    private String packageType;

    @Column(name = "unit")
    private String unit="Kg";

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "price")
    private Double price;

    @Column(name = "import_date")
    private Date importDate;

    @Column(name = "expired_date")
    private Date exppiredDate;

    @Column(name = "document")
    private String document;

//    @Column(name = "status")
//    private String status;

    @Column(name = "QR")
    private String qr;



    @JoinTable(
            name = "bin_slot",
            joinColumns = @JoinColumn(name = "bin_id"),
            inverseJoinColumns = @JoinColumn(name = "slot_id"))
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private List<Slot> slots;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "quality_id", referencedColumnName = "id")
    private Quality quality;



    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "transfer_ticket_id", referencedColumnName = "id")
    private TransferTicket transferTicket;

    public void addSlot(Slot slot) {
        if(this.slots==null){
            this.slots = new ArrayList<>();
            this.slots.add(slot);
            if(slot.getBins()!=null){
                slot.getBins().add(this);
            } else{
                List<Bin> bins = new ArrayList<>();
                bins.add(this);
                slot.setBins(bins);
            }
        }
    }

}

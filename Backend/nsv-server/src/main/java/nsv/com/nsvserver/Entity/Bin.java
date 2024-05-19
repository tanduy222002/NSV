package nsv.com.nsvserver.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bin {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "weight")
    private Double weight=0.0;

    @Column(name = "left_weight")
    private Double leftWeight=weight;

    @Column(name = "package_type")
    private String packageType;

    @Column(name = "unit")
    private String unit="Kg";

    @Column(name = "status")
    private String status="PENDING";

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "price")
    private Double price;

    @Column(name = "import_date")
    private Date importDate;

    @Column(name = "export_date")
    private Date exportDate;

    @Column(name = "document")
    private String document;

//    @Column(name = "status")
//    private String status;

    @Column(name = "QR")
    private String qr;



    @OneToMany(cascade = CascadeType.ALL,mappedBy = "bin")
    private List<BinSlot> binSlot;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "quality_id", referencedColumnName = "id")
    private Quality quality;



    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "transfer_ticket_id", referencedColumnName = "id")
    private TransferTicket transferTicket;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "exportBin")
    private List<BinBin> importBins;

//    @OneToMany(cascade = CascadeType.ALL,mappedBy = "exportBin")
//    private List<BinBin> exportBins;

    public void setWeight(Double weight) {
        this.weight = weight;
        this.leftWeight = weight;
    }

    public Bin(Integer id, Double weight, Double leftWeight, String packageType, String unit, String status, Integer amount, Double price, Date importDate, Date exportDate, String document, String qr) {
        this.id = id;
        this.weight = weight;
        this.leftWeight = leftWeight;
        this.packageType = packageType;
        this.unit = unit;
        this.status = status;
        this.amount = amount;
        this.price = price;
        this.importDate = importDate;
        this.exportDate = exportDate;
        this.document = document;
        this.qr = qr;
    }
}

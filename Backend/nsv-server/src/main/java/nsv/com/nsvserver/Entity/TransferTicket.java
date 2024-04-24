package nsv.com.nsvserver.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferTicket {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String statue;

    @Column(name = "qr")
    private String qr;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "approved_date")
    private Date approvedDate;

    @Column(name = "transport_date")
    private Date transportDate;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "create_employee_id", referencedColumnName = "id")
    private Employee createEmployee;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_employee_id", referencedColumnName = "id")
    private Employee approvedEmployee;


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id", referencedColumnName = "id")
    private Partner partner;

    @OneToMany(mappedBy = "transferTicket"
            ,cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Bin> bins;

    @OneToOne(mappedBy = "transferTicket",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Debt debt;

    public void addDebt(Debt debt){
        this.debt=debt;
        debt.setTransferTicket(this);
    }



}

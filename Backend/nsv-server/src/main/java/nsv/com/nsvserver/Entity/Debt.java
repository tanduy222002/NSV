package nsv.com.nsvserver.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity(name = "debt")
public class Debt {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "note")
    private String note;

    @Column(name = "is_paid")
    private Boolean isPaid = false;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "unit")
    private String unit= "VND";

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "transfer_ticket_id", referencedColumnName = "id")
    private TransferTicket transferTicket;

    @OneToMany(mappedBy="debt",cascade = CascadeType.ALL)
    private List<PaymentHistory> paymentHistories;


}

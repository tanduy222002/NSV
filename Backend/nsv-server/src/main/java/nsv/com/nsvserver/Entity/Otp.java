package nsv.com.nsvserver.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "Otp")
@Data
public class Otp {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    @NotNull
    private String code;

    @Column(name = "expiry_date")
    @NotNull
    private Date expiryDate;

    public Otp(String code, Date expiryDate) {
        this.code = code;
        this.expiryDate = expiryDate;
    }
    @OneToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,
    },fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id",referencedColumnName = "id")
    private Employee employee;

    public Otp() {
    }
}

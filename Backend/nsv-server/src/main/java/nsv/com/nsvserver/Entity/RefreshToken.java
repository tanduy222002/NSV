package nsv.com.nsvserver.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Refresh_Token")
public class RefreshToken {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "token")
    private String token;
    @Column(name = "expiry_date")
    private Date expiryDate;

    public RefreshToken() {
    }

    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,
            CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH
    })
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private Employee employee;

}

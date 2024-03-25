package nsv.com.nsvserver.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "Employee")
@Getter
@Setter
public class Employee {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name",unique = true, nullable = false)
    private String userName;

    @Column(name = "pass_word",nullable = false)
    private String password;

    @Column(name = "status")
    private String status;

    @ManyToMany(fetch = FetchType.EAGER ,cascade = {CascadeType.DETACH,
            CascadeType.MERGE,CascadeType.PERSIST
    })
    @JoinTable(name = "Employee_Role",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private RefreshToken refreshToken;


    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Otp otp;

    public Employee() {
    }

    public Employee(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public void addRole(Role role){
        this.roles.add(role);
    }

    public void setRefreshToken(RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
        if(refreshToken != null){
        refreshToken.setEmployee(this);
        }
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
        profile.setEmployee(this);
    }

    public void setOtp(Otp otp) {
        this.otp = otp;
        if(otp != null){
        otp.setEmployee(this);
        }
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", roles=" + roles +
                ", profile=" + profile +
                ", refreshToken=" + refreshToken +
                ", otp=" + otp +
                '}';
    }
}

package nsv.com.nsvserver.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nsv.com.nsvserver.Dto.EmployeeDto;
import nsv.com.nsvserver.Dto.ProfileDto;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Profile")
public class Profile {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "gender")
    private String gender;
    @OneToOne(mappedBy = "profile",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private Employee employee;


    @OneToOne(mappedBy="profile",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private Address address;
    public Profile(String name, String phoneNumber, String email, String gender, Employee employee) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.employee = employee;
    }

    public Profile() {
    }


    public Profile(ProfileDto employeeDto, Integer ID) {
        this.name= employeeDto.getName();
        this.id= ID;
        this.email= employeeDto.getEmail();
        this.gender=employeeDto.getGender();
        this.phoneNumber=employeeDto.getPhoneNumber();

    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}

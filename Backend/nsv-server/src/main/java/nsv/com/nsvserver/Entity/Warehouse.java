package nsv.com.nsvserver.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Warehouse")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type",nullable = false)
    private String type;

    @Column(name = "capacity",nullable = false)
    private Double capacity=0.0;

    @Column(name = "containing",nullable = false)
    private Double containing=0.0;

    @Column(name = "status")
    private String status="Hoạt động";

    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "map_id", referencedColumnName = "id")
    private Map map;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;







}

package nsv.com.nsvserver.Repository;


import nsv.com.nsvserver.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    public List<Employee> findAll();
    public boolean existsByUserName(String userName);
    public Employee findByUserName(String userName);


    @Query("SELECT e FROM Employee e JOIN FETCH e.profile p WHERE p.email = :email")
    public Optional<Employee> findByEmail(String email);

    public boolean existsById(Integer id);







}

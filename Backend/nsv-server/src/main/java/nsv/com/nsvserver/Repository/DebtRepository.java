package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.Debt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebtRepository extends JpaRepository<Debt,Integer> {
}

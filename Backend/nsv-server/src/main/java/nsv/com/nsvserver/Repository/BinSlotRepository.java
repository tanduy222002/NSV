package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.BinSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BinSlotRepository extends JpaRepository<BinSlot,Integer> {
}

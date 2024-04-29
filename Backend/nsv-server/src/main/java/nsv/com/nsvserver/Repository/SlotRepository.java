package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SlotRepository extends JpaRepository<Slot,Integer> {

    @Query(" from Slot s join row as r join r.map as m join m.warehouse where s.id =:id")
    Optional<Slot> findByIdJoinWithMap(@Param("id") Integer id);
}

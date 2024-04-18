package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapRepository extends JpaRepository<Map,Integer> {
}

package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner,Integer> {
}

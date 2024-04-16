package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Integer> {
}

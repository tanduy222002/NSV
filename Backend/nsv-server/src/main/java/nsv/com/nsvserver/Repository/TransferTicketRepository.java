package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.TransferTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferTicketRepository extends JpaRepository<TransferTicket,Integer> {
}

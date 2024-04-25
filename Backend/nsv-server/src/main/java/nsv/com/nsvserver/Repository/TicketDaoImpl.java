package nsv.com.nsvserver.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nsv.com.nsvserver.Entity.Profile;
import nsv.com.nsvserver.Entity.TransferTicket;
import org.springframework.data.annotation.Persistent;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketDaoImpl implements TicketDao{

    @PersistenceContext
    EntityManager entityManager;

    public TicketDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public TransferTicket fetchWithBinAndSlot(Integer Id) {
        TransferTicket transferTicket = entityManager.createQuery("select t from TransferTicket t join t.bins as b join b.slots", TransferTicket.class).getSingleResult();
        return transferTicket;
    }
}

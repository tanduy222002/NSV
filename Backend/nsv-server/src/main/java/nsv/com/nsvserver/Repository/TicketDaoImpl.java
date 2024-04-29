package nsv.com.nsvserver.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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
        Query query = entityManager.createQuery("select t from TransferTicket t join t.bins as b join b.binSlot as bs join bs.slot as s join s.row as r join r.map as m Where t.id = :ticketId");
        query.setParameter("ticketId",Id);
        return (TransferTicket) query.getSingleResult();
    }
}

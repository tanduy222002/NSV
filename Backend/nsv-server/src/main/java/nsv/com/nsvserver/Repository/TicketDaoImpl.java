package nsv.com.nsvserver.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import nsv.com.nsvserver.Entity.TransferTicket;
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

    @Override
    public List<TransferTicket> getTicketWithFilterAndPagination(Integer pageIndex, Integer pageSize, String name, String type, String status) {
        StringBuilder queryString = new StringBuilder(
                """
                SELECT tt FROM TransferTicket tt join tt.bins as b join b.quality as q join q.type as t join t.product as p join b.binSlot as bs join bs.slot as s join s.row as r WHERE 1=1 """
        );



        if(name!=null){
            queryString.append(" AND tt.name LIKE:namePattern");
        }
        if(type!=null){
            queryString.append(" AND tt.type LIKE:typePattern");
        }
        if(status!=null){
            queryString.append(" AND tt.status LIKE:statusPattern");
        }

        Query query = entityManager.createQuery(queryString.toString());

        if(name!=null){
            query.setParameter("namePattern","%"+name+"%");
        }

        if(type!=null){
            query.setParameter("typePattern","%"+type+"%");
        }

        if(status!=null){
            query.setParameter("statusPattern","%"+status+"%");
        }

        List<TransferTicket> resultList= query.setFirstResult((pageIndex-1)*pageSize)
                .setMaxResults(pageSize)
                .getResultList();
        return resultList;
    }

    @Override
    public long countTotalTicketWithFilterAndPagination(Integer pageIndex, Integer pageSize, String name, String type, String status) {
        StringBuilder queryString = new StringBuilder(
                """
                SELECT COUNT(t.id) FROM TransferTicket t WHERE 1=1 """
        );



        if(name!=null){
            queryString.append(" AND t.name LIKE:namePattern");
        }
        if(type!=null){
            queryString.append(" AND t.type LIKE:typePattern");
        }
        if(status!=null){
            queryString.append(" AND t.status LIKE:statusPattern");
        }

        Query query = entityManager.createQuery(queryString.toString());

        if(name!=null){
            query.setParameter("namePattern","%"+name+"%");
        }

        if(type!=null){
            query.setParameter("typePattern","%"+type+"%");
        }

        if(status!=null){
            query.setParameter("statusPattern","%"+status+"%");
        }

        Long count = (Long) query.getSingleResult();
        return count;
    }

}

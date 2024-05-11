package nsv.com.nsvserver.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import nsv.com.nsvserver.Entity.Bin;
import nsv.com.nsvserver.Entity.BinBin;
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
        Query query = entityManager.createQuery("select t from TransferTicket t left join fetch t.debt as d join fetch t.bins as b join b.binSlot as bs join fetch bs.slot as s join fetch s.row as r join fetch r.map as m join fetch m.warehouse Where t.id = :ticketId");
        query.setParameter("ticketId",Id);
        return (TransferTicket) query.getSingleResult();
    }

    @Override
    public List<TransferTicket> getTicketWithFilterAndPagination(Integer pageIndex, Integer pageSize, String name, String type, String status) {
        StringBuilder queryString = new StringBuilder(
                """
                SELECT tt FROM TransferTicket tt left join fetch tt.debt as d left join fetch tt.bins as b left join b.binSlot as bs left join fetch bs.slot as s left join fetch s.row as r
                join fetch b.quality as q join fetch q.type as t 
                join fetch t.product as p WHERE 1=1 """
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

        System.out.println(pageSize);
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

    @Override
    public TransferTicket fetchExportTicket(Integer Id) {
        Query query = entityManager.createQuery("select t from TransferTicket t left join fetch t.debt join fetch t.bins as b" +
                " Where t.id = :ticketId");
//        "join b.importBins as ib join ib.importSlot as il join il.row as r join  r.map as m " +
//                "join m.warehouse
        query.setParameter("ticketId",Id);
        return (TransferTicket) query.getSingleResult();
    }

    @Override
    public List<BinBin> fetchBinBinByExportBinId(Integer Id) {
        Query query = entityManager.createQuery("select bb From BinBin bb join fetch bb.exportBin as eb join fetch bb.importBin as ib " +
                "join fetch bb.importSlot as s " +
                " Where eb.id = :binId ");
        query.setParameter("binId",Id);
        return  query.getResultList();
    }


    @Override
    public List<Bin> getImportBinInTicketDetail(Integer Id) {
        StringBuilder queryString = new StringBuilder(
                """
                SELECT b FROM Bin b join fetch b.transferTicket as tt left join fetch b.binSlot as bs left join fetch bs.slot as s left join fetch s.row as r left join fetch r.map as m left join fetch m.warehouse as w
                 left join fetch b.quality as q left join fetch q.type as t left join fetch t.product as p Where tt.id=:ticketId """
        );
        Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("ticketId",Id);
        return query.getResultList();
    }

    @Override
    public TransferTicket getTicketDetail(Integer Id) {
        StringBuilder queryString = new StringBuilder(
                """
                SELECT tt FROM TransferTicket tt 
                 left join fetch tt.partner as p left join fetch p.profile as pr left join fetch pr.address as a 
                 left join fetch a.ward as w left join fetch w.district as dt left join fetch dt.province as pvc
                 left join fetch tt.debt as d Where tt.id=:ticketId """
        );
        Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("ticketId",Id);
        return (TransferTicket) query.getSingleResult();
    }



}

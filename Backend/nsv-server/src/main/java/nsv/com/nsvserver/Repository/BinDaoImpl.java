package nsv.com.nsvserver.Repository;

import jakarta.persistence.*;
import nsv.com.nsvserver.Annotation.TraceTime;
import nsv.com.nsvserver.Entity.Bin;
import nsv.com.nsvserver.Entity.BinSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BinDaoImpl implements BinDao {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public BinDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @TraceTime
    public List<Bin> findWithFilterAndPagination(Integer pageIndex, Integer pageSize, Integer warehouseId, Integer productId, Integer typeId, Integer qualityId, Integer minWeight, Integer maxWeight) {

//        }
        StringBuilder queryString = new StringBuilder(
                """
                SELECT b FROM Bin b join fetch b.binSlot as bs join fetch bs.slot as s join fetch s.row as r join fetch r.map as m join fetch m.warehouse as w
                 join fetch b.quality as q join fetch q.type as t join fetch t.product as p WHERE b.status = 'APPROVED'"""
        );






        if(qualityId!=null){
            queryString.append(" And q.id=:qualityId");
        }
        if(warehouseId!=null){
            queryString.append("  And w.id=:warehouseId");
        }

        if(typeId!=null){
            queryString.append(" And t.id=:typeId");
        }
        if(productId!=null){
            queryString.append(" And p.id=:productId");
        }


        if(minWeight!=null){
            queryString.append(" And b.leftWeight >= :minWeight");
        }

        if(maxWeight!=null){
            queryString.append(" And b.leftWeight <= :maxWeight");
        }



        Query query = entityManager.createQuery(queryString.toString());

        if(qualityId!=null){
            query.setParameter("qualityId",qualityId);
        }


        if(typeId!=null){
            query.setParameter("typeId",typeId);
        }


        if(productId!=null){
            query.setParameter("productId",productId);
        }

        if(warehouseId!=null){
            query.setParameter("warehouseId",warehouseId);
        }



        if(minWeight!=null){
           query.setParameter("minWeight",minWeight);
        }

        if(maxWeight!=null){
            query.setParameter("maxWeight",maxWeight);
        }

        List<Bin> resultList= query.setFirstResult((pageIndex-1)*pageSize)
                .setMaxResults(pageSize)
                .getResultList();
        return resultList;
    }


    @Override
    @TraceTime
    public long countTotalBinWithFilterAndPagination(Integer pageIndex, Integer pageSize, Integer warehouseId, Integer productId, Integer typeId, Integer qualityId, Integer minWeight, Integer maxWeight) {
        StringBuilder queryString = new StringBuilder(
                """
                SELECT COUNT(bs.id) FROM BinSlot as bs join bs.bin as b join bs.slot as s join b.quality as q"""
        );

        if(qualityId!=null){
            queryString.append(" on q.id=:qualityId");
        }
        queryString.append(" join s.row as r join r.map as m join m.warehouse as w");

        if(warehouseId!=null){
            queryString.append("  on w.id=:warehouseId");
        }

        queryString.append(" join q.type as t");

        if(typeId!=null){
            queryString.append(" on t.id=:typeId");
        }

        queryString.append(" join t.product as p");

        if(productId!=null){
            queryString.append(" on p.id=:productId");
        }


        queryString.append(" WHERE b.status = 'APPROVED'");


        if(minWeight!=null){
            queryString.append(" And b.leftWeight >= :minWeight");
        }

        if(maxWeight!=null){
            queryString.append(" And b.leftWeight <= :maxWeight");
        }



        Query query = entityManager.createQuery(queryString.toString());

        if(qualityId!=null){
            query.setParameter("qualityId",qualityId);
        }


        if(typeId!=null){
            query.setParameter("typeId",typeId);
        }


        if(productId!=null){
            query.setParameter("productId",productId);
        }

        if(warehouseId!=null){
            query.setParameter("warehouseId",warehouseId);
        }



        if(minWeight!=null){
            query.setParameter("minWeight",minWeight);
        }

        if(maxWeight!=null){
            query.setParameter("maxWeight",maxWeight);
        }


        return (Long) query.getSingleResult();
    }

    @Override
    @TraceTime
    public Bin findBinInSlotBySlotIdAndBinId(Integer binId, Integer slotId) {
        StringBuilder queryString = new StringBuilder(
                """
                SELECT b FROM Bin b join fetch b.quality as q join fetch q.type as t join fetch t.product as p
                 join fetch b.binSlot as bs join fetch bs.slot as s join fetch s.row as r join fetch r.map as m join fetch m.warehouse as w
                 WHERE s.id=:slotId AND b.id=:binId AND b.status = 'APPROVED'"""
        );
        Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("slotId",slotId);
        query.setParameter("binId",binId);
        Bin bin=(Bin) query.getSingleResult();
        return bin;


    }

    @Override
    public BinSlot GetBinSlotBySlotIdAndBinId(Integer binId, Integer slotId) {
        StringBuilder queryString = new StringBuilder(
                """
                SELECT bs FROM BinSlot as bs join fetch bs.slot as s join fetch bs.bin as b
                join fetch b.quality as q join fetch q.type as t join fetch t.product as p WHERE s.id=:slotId AND b.id=:binId AND b.status = 'APPROVED'"""
        );
        Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("slotId",slotId);
        query.setParameter("binId",binId);
        BinSlot binSlot=(BinSlot) query.getSingleResult();
        return binSlot;


    }
}

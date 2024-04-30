package nsv.com.nsvserver.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import nsv.com.nsvserver.Dto.StatisticOfProductInWarehouseDto;
import nsv.com.nsvserver.Dto.StatisticOfTypeAndQuality;
import nsv.com.nsvserver.Dto.WarehouseDto;
import nsv.com.nsvserver.Entity.Bin;
import nsv.com.nsvserver.Entity.Slot;
import nsv.com.nsvserver.Entity.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class SlotDaoImpl implements SlotDao {
    EntityManager entityManager;

    @Autowired
    public SlotDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Bin> getSlotDetail(Integer slotId, Integer pageIndex, Integer pageSize) {
        Query query = entityManager.createQuery(
                        "select b from Bin b " +
                        " join b.quality as q join b.transferTicket as tk" +
                        " join q.type as t join t.product as p"+
                        " join b.binSlot as bs join bs.slot as s WHERE s.id =:slotId");

        query.setParameter("slotId",slotId);
        List<Bin> resultList= query.setFirstResult((pageIndex-1)*pageSize)
                .setMaxResults(pageSize)
                .getResultList();
        System.out.println(resultList.get(0));
        return resultList;
    }

    @Override
    public long countGetSlotDetail(Integer slotId, Integer pageIndex, Integer pageSize) {
        Query query = entityManager.createQuery(
                "select COUNT(b.id) from Bin b join b.binSlot as bs join bs.slot as s on s.id =: slotId" );
        query.setParameter("slotId",slotId);
        long count = (long) query.getSingleResult();
        return count;
    }
}

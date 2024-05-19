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
                " select b from Bin b " +
                " join b.binSlot as bs join bs.slot as s on s.id =:slotId"+
                " join b.quality as q join q.type as t join t.product as p join b.transferTicket as tk");
        query.setParameter("slotId",slotId);
        List<Bin> resultList= query.setFirstResult((pageIndex-1)*pageSize)
                .setMaxResults(pageSize)
                .getResultList();

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


    @Override
    public List<StatisticOfProductInWarehouseDto> getStatisticsOfProductInSlot(Integer slotId) {
        String queryString = "select p.name,p.image,t.name,t.image,q.name,sum(bs.weight) from "+
                "Product p join p.types as t " +
                "join t.qualities as q join q.bin as b join " +
                "b.binSlot as bs join bs.slot as s on s.id =: slotId " +
                "group by p.name,p.image,t.name,t.image,q.name";

        TypedQuery<Object[]> query = entityManager.createQuery(queryString,Object[].class);
        query.setParameter("slotId",slotId);
        List<Object[]> result = query.getResultList();


        Map<String, StatisticOfProductInWarehouseDto> productInfoMap = Collections.synchronizedMap(new HashMap<>());

        result.parallelStream().forEach(row->{
            String productName = (String) row[0];
            String productImg = (String) row[1];
            String typeName = (String) row[2];
            String typeImg = (String) row[3];
            String qualityName = (String) row[4];
            Double weight = (Double) row[5];

            productInfoMap.computeIfAbsent(productName,
                    key -> new StatisticOfProductInWarehouseDto(productName,productImg)
            );
            StatisticOfTypeAndQuality typeAndQuality = new StatisticOfTypeAndQuality();
            typeAndQuality.setName(typeName+" "+qualityName);
            typeAndQuality.setTypeImg(typeImg);
            typeAndQuality.setWeight(weight);
            productInfoMap.get(productName).addType(typeAndQuality);
        });

        return productInfoMap.values().parallelStream().collect(Collectors.toList());

    }

    @Override
    public Slot getSlotContainingAndCapacityById(Integer slotId) {
        Query query = entityManager.createQuery(
                "select new nsv.com.nsvserver.Entity.Slot(s.id,s.name,s.capacity,s.containing) from Slot s WHERE s.id =: slotId" );
        query.setParameter("slotId",slotId);
        return (Slot) query.getSingleResult();
    }
}

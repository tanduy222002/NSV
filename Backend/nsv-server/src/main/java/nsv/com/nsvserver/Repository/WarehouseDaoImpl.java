package nsv.com.nsvserver.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import nsv.com.nsvserver.Dto.StatisticOfProductInWarehouseDto;
import nsv.com.nsvserver.Dto.StatisticOfTypeAndQuality;
import nsv.com.nsvserver.Entity.Slot;
import nsv.com.nsvserver.Entity.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class WarehouseDaoImpl implements WarehouseDao {
    EntityManager entityManager;

    @Autowired
    public WarehouseDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<StatisticOfProductInWarehouseDto> getStatisticsOfProductInWarehouse(Integer warehouseId) {
        String queryString = "select p.name,p.image,t.name,t.image,q.name,sum(distinct b.weight) from Product p join p.types as t " +
                "join t.qualities as q join q.bin as b join " +
                "b.slots as s join s.row as r " +
                "join r.map as m join m.warehouses as w " +
                "on w.id =: warehouseId " +
                "group by p.name,p.image,t.name,t.image,q.name";

        TypedQuery<Object[]> query = entityManager.createQuery(queryString,Object[].class);
        query.setParameter("warehouseId",warehouseId);
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
    public Slot fetchSlot(Integer Id) {
        Query query = entityManager.createQuery("select s from Slot s join s.warehouse as w Where w.id = :warehouseId");
        query.setParameter("warehouseId",Id);
        return (Slot) query.getResultList();
    }

    @Override
    public Warehouse getMapDetail(Integer warehouseId) {
        Query query = entityManager.createQuery(
                "select w from Warehouse w join w.map as m join m.rows as r join r.slots as s" +
                        " Where w.id = :warehouseId");
        query.setParameter("warehouseId",warehouseId);
        return (Warehouse) query.getSingleResult();
    }
}

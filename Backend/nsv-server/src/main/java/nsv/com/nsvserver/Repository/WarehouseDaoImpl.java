package nsv.com.nsvserver.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import nsv.com.nsvserver.Dto.SlotWithIdAndNameDto;
import nsv.com.nsvserver.Dto.StatisticOfProductInWarehouseDto;
import nsv.com.nsvserver.Dto.StatisticOfTypeAndQuality;
import nsv.com.nsvserver.Dto.WarehouseDto;
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
        String queryString = "select p.name,p.image,t.name,t.image,q.name,sum(bs.weight) from "+
                "Product p join p.types as t " +
                "join t.qualities as q join q.bin as b join " +
                "b.binSlot as bs join bs.slot as s join s.row as r " +
                "join r.map as m join m.warehouse as w " +
                "where w.id =: warehouseId " +
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
    public List<WarehouseDto> getWarehouses(Integer pageIndex, Integer pageSize, String name, String type, String status) {
        StringBuilder queryString = new StringBuilder(
                """
                    select new nsv.com.nsvserver.Dto.WarehouseDto(w.id,w.name,w.type,a,w.containing,w.capacity,w.status)
                    from Warehouse w join w.address as a join fetch a.ward as wa join fetch wa.district as d join fetch d.province as p WHERE 1=1 """
        );



        if(name!=null){
            queryString.append(" AND w.name LIKE:namePattern");
        }
        if(type!=null){
            queryString.append(" AND w.type LIKE:typePattern");
        }
        if(status!=null){
            queryString.append(" AND w.status LIKE:statusPattern");
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

        List<WarehouseDto> resultList= query.setFirstResult((pageIndex-1)*pageSize)
                .setMaxResults(pageSize)
                .getResultList();
        return resultList;
    }

    @Override
    public long countTotalGetWarehouse(Integer pageIndex, Integer pageSize, String name, String type, String status) {
        StringBuilder queryString = new StringBuilder(
                """
                    select COUNT(w.id)
                    from Warehouse w WHERE 1=1 """
        );



        if(name!=null){
            queryString.append(" AND w.name LIKE:namePattern");
        }
        if(type!=null){
            queryString.append(" AND w.type LIKE:typePattern");
        }
        if(status!=null){
            queryString.append(" AND w.status LIKE:statusPattern");
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
    public Warehouse getMapDetail(Integer warehouseId) {
        Query query = entityManager.createQuery(
                "select w from Warehouse w join w.map as m join m.rows as r join r.slots as s" +
                        " Where w.id = :warehouseId");
        query.setParameter("warehouseId",warehouseId);
        return (Warehouse) query.getSingleResult();
    }


}

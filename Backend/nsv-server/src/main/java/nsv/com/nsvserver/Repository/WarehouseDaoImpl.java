package nsv.com.nsvserver.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import nsv.com.nsvserver.Entity.TransferTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WarehouseDaoImpl implements WarehouseDao {
    EntityManager entityManager;

    @Autowired
    public WarehouseDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<?> getStatisticsOfProductInWarehouse(Integer warehouseId) {
        String queryString = "select p.id,t.id,sum(b.weight) from Product p join p.types as t " +
                "join t.qualities as q join q.bin as b join " +
                "b.slots as s join s.row as r " +
                "join r.map as m join m.warehouses as w " +
                "on w.id =: warehouseId group by p.id,t.id";

        TypedQuery<Object[]> query = entityManager.createQuery(queryString,Object[].class);
        query.setParameter("warehouseId",warehouseId);
        List<Object[]> result = query.getResultList();
        System.out.println(result.get(0)[2]);
        return new ArrayList<>();

    }
}

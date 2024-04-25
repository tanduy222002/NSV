package nsv.com.nsvserver.Repository;

import java.util.List;

public interface WarehouseDao {
    List<?> getStatisticsOfProductInWarehouse(Integer warehouseId);
}

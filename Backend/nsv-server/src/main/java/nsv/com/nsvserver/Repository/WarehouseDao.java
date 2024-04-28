package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.Map;
import nsv.com.nsvserver.Entity.Slot;
import nsv.com.nsvserver.Entity.Warehouse;

import java.util.List;

public interface WarehouseDao {
    List<?> getStatisticsOfProductInWarehouse(Integer warehouseId);
    public Slot fetchSlot(Integer Id);

    public Warehouse getMapDetail(Integer mapId);
}

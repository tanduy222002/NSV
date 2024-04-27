package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.Slot;
import java.util.List;

public interface WarehouseDao {
    List<?> getStatisticsOfProductInWarehouse(Integer warehouseId);
    public Slot fetchSlot(Integer Id);
}

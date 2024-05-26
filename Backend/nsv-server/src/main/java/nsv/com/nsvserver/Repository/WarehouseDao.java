package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Dto.WarehouseDto;
import nsv.com.nsvserver.Entity.Map;
import nsv.com.nsvserver.Entity.Slot;
import nsv.com.nsvserver.Entity.Warehouse;

import java.util.List;

public interface WarehouseDao {
    List<?> getStatisticsOfProductInWarehouse(Integer warehouseId);
//    public Slot fetchSlot(Integer Id);

    public Warehouse getMapDetail(Integer mapId);

    public List<WarehouseDto> getWarehouses(Integer pageIndex, Integer pageSize, String name, String type, String status);

    long countTotalGetWarehouse(Integer pageIndex, Integer pageSize, String name, String type, String status);

}

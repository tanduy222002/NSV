package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Dto.StatisticOfProductInWarehouseDto;
import nsv.com.nsvserver.Dto.WarehouseDto;
import nsv.com.nsvserver.Entity.Bin;
import nsv.com.nsvserver.Entity.Slot;
import nsv.com.nsvserver.Entity.Warehouse;

import java.util.List;

public interface SlotDao {

    public List<Bin> getSlotDetail(Integer slotId, Integer pageIndex, Integer pageSize);
    public long countGetSlotDetail(Integer slotId, Integer pageIndex, Integer pageSize);
    public List<StatisticOfProductInWarehouseDto> getStatisticsOfProductInSlot(Integer slotId);

    public Slot getSlotContainingAndCapacityById(Integer slotId);
}

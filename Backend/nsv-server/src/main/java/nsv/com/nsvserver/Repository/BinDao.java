package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.Bin;

import java.util.List;
import java.util.Optional;

public interface BinDao {
    List<Bin> findWithFilterAndPagination(Integer pageIndex, Integer pageSize, Integer warehouseId,
                                          Integer productId, Integer typeId, Integer qualityId,
                                          Integer minWeight, Integer maxWeight);
    long countTotalBinWithFilterAndPagination(Integer pageIndex, Integer pageSize, Integer warehouseId,
                                              Integer productId, Integer typeId, Integer qualityId,
                                              Integer minWeight, Integer maxWeight);
    Bin findBinInSlotBySlotIdAndBinId(Integer binId, Integer slotId);
}

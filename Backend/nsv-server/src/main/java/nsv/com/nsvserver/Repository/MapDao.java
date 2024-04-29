package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Dto.MapWithIdAndNameDto;
import nsv.com.nsvserver.Dto.SlotWithIdAndNameDto;

import java.util.List;

public interface MapDao {
    List<MapWithIdAndNameDto> getMapIdAndNameWithFilterAndPagination(Integer pageIndex, Integer pageSize, String name);

    List<MapWithIdAndNameDto> getMapIdAndNameWithFilterAndPagination();
    Long countTotalHasName(String name);

    Long countTotalSlotInMapWithFilter(String name, Integer mapId);

    List<SlotWithIdAndNameDto> getSlotIdAndNameInMapWithFilterAndPagination(Integer pageIndex, Integer pageSize, String name, Integer mapId);
}

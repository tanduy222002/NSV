package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Dto.MapWithIdAndNameDto;

import java.util.List;

public interface MapDao {
    List<MapWithIdAndNameDto> getMapIdAndNameWithFilterAndPagination(Integer pageIndex, Integer pageSize, String name);
    Long countTotalHasName(String name);
}

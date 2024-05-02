package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Dto.QualityDto;
import nsv.com.nsvserver.Dto.TypeDto;

import java.util.List;

public interface ProductDao {
    List<TypeDto> getTypesInProductByProductId(Integer productId);
    List<QualityDto> getQualitiesInTypeByTypeId(Integer typeId);


}

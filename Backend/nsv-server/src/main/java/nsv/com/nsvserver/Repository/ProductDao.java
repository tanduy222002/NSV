package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Dto.ProductDetailDto;
import nsv.com.nsvserver.Dto.QualityDto;
import nsv.com.nsvserver.Dto.TypeDto;

import java.util.List;

public interface ProductDao {
    List<TypeDto> getTypesInProductByProductId(Integer productId);
    List<QualityDto> getQualitiesInTypeByTypeId(Integer typeId);



    List<ProductDetailDto> getProductDetails(Integer pageIndex, Integer pageSize, String name);

    long countTotalProductDetailsWithFilterAndPagination(String name);
}

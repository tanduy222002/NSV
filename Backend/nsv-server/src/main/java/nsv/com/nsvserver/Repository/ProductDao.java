package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Dto.*;

import java.util.List;

public interface ProductDao {
    List<TypeDto> getTypesInProductByProductId(Integer productId);
    List<QualityDto> getQualitiesInTypeByTypeId(Integer typeId);



    List<ProductDetailDto> getProductDetails(Integer pageIndex, Integer pageSize, String name);

    long countTotalProductDetailsWithFilterAndPagination(String name);

    List<ProductTypeWithQualityDetailInSlotDto> getStatisticsOfProduct(Integer productId);
}

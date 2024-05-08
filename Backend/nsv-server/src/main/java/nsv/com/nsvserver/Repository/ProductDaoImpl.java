package nsv.com.nsvserver.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import nsv.com.nsvserver.Dto.ProductDetailDto;
import nsv.com.nsvserver.Dto.QualityDto;
import nsv.com.nsvserver.Dto.TypeDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao{

    @PersistenceContext
    EntityManager entityManager;

    public ProductDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }



    @Override
    public List<TypeDto> getTypesInProductByProductId(Integer productId) {
        StringBuilder queryString = new StringBuilder(
                """
                SELECT new nsv.com.nsvserver.Dto.TypeDto(t.id,t.name) from Type t join t.product as p on p.id = : productId """
        );





        Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("productId",productId);

        List<TypeDto> resultList= query
                .getResultList();
        return resultList;
    }

    @Override
    public List<QualityDto> getQualitiesInTypeByTypeId(Integer typeId) {
        StringBuilder queryString = new StringBuilder(
                """
                SELECT new nsv.com.nsvserver.Dto.QualityDto(q.id,q.name) from Quality q join q.type as t on t.id = : typeId """
        );



        Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("typeId",typeId);

        List<QualityDto> resultList= query
                .getResultList();
        return resultList;
    }

    @Override
    public List<ProductDetailDto> getProductDetails(Integer pageIndex, Integer pageSize, String name) {
        StringBuilder queryString = new StringBuilder(
                """
                SELECT new nsv.com.nsvserver.Dto.ProductDetailDto(p.id,p.name,p.image,sum(b.leftWeight) ) FROM Product p 
                join p.types as t join t.qualities as q left join q.bin as b  WHERE 1=1 """
        );
        //left join b.binSlot as bs


        if(name!=null){
            queryString.append(" AND p.name LIKE:namePattern");
        }
        queryString.append(" GROUP BY p.id");

        Query query = entityManager.createQuery(queryString.toString());

        if(name!=null){
            query.setParameter("namePattern","%"+name+"%");
        }


        List<ProductDetailDto> resultList= query.setFirstResult((pageIndex-1)*pageSize)
                .setMaxResults(pageSize)
                .getResultList();
        return resultList;
    }

    @Override
    public long countTotalProductDetailsWithFilterAndPagination(String name) {
        StringBuilder queryString = new StringBuilder(
                """
                SELECT COUNT(p.id) from Product p 
                """
        );


        if(name!=null){
            queryString.append(" AND p.name LIKE:namePattern");
        }

        Query query = entityManager.createQuery(queryString.toString());

        if(name!=null){
            query.setParameter("namePattern","%"+name+"%");
        }


        return (long) query.getSingleResult();

    }
}

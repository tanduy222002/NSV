package nsv.com.nsvserver.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import nsv.com.nsvserver.Dto.QualityDto;
import nsv.com.nsvserver.Dto.TypeDto;
import nsv.com.nsvserver.Entity.Quality;
import nsv.com.nsvserver.Entity.TransferTicket;
import nsv.com.nsvserver.Entity.Type;
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


}

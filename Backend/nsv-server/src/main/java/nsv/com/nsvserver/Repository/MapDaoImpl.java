package nsv.com.nsvserver.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import nsv.com.nsvserver.Dto.MapWithIdAndNameDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MapDaoImpl implements MapDao {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public MapDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<MapWithIdAndNameDto> getMapIdAndNameWithFilterAndPagination(Integer page, Integer pageSize, String name) {
        StringBuilder queryString = new StringBuilder(
                "Select New nsv.com.nsvserver.Dto.MapWithIdAndNameDto(m.id, m.name) FROM Map m WHERE 1=1"
        );

        if(name!=null){
            queryString.append(" AND m.name LIKE:namePattern");
        }

        Query query = entityManager.createQuery(queryString.toString());

        if(name!=null){
            query.setParameter("namePattern","%"+name+"%");

        }

        List<MapWithIdAndNameDto> resultList= query.setFirstResult((page-1)*pageSize)
                .setMaxResults(pageSize)
                .getResultList();

        return resultList;

    }

    @Override
    public Long countTotalHasName(String name) {
        StringBuilder queryString = new StringBuilder(
                "Select COUNT(m.id) FROM Map m WHERE 1=1"
        );

        if(name!=null){
            queryString.append(" AND m.name LIKE:namePattern");
        }

        Query query = entityManager.createQuery(queryString.toString());

        if(name!=null){
            query.setParameter("namePattern","%"+name+"%");

        }
        Long count = (Long) query.getSingleResult();
        return count;
    }

}

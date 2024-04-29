package nsv.com.nsvserver.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import nsv.com.nsvserver.Dto.MapWithIdAndNameDto;
import nsv.com.nsvserver.Dto.SlotWithIdAndNameDto;
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

    @Override
    public List<SlotWithIdAndNameDto> getSlotIdAndNameInMapWithFilterAndPagination(Integer pageIndex, Integer pageSize, String name,Integer mapId) {
        StringBuilder queryString = new StringBuilder(
                "Select New nsv.com.nsvserver.Dto.SlotWithIdAndNameDto(s.id, s.name) FROM Map m join m.rows as r join r.slots as s WHERE m.id=:mapId"
        );

        if(name!=null){
            queryString.append(" AND s.name LIKE:namePattern");
        }

        Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("mapId",mapId);
        if(name!=null){
            query.setParameter("namePattern","%"+name+"%");

        }

        List<SlotWithIdAndNameDto> resultList= query.setFirstResult((pageIndex-1)*pageSize)
                .setMaxResults(pageSize)
                .getResultList();

        return resultList;
    }

    @Override
    public Long countTotalSlotInMapWithFilter(String name, Integer mapId) {
        StringBuilder queryString = new StringBuilder(
                "Select COUNT(s.id) FROM Map m join m.rows as r join r.slots as s WHERE m.id=:mapId"
        );

        if(name!=null){
            queryString.append(" AND m.name LIKE:namePattern");
        }

        Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("mapId",mapId);
        if(name!=null){
            query.setParameter("namePattern","%"+name+"%");
        }
        Long count = (Long) query.getSingleResult();
        return count;
    }


    @Override
    public List<MapWithIdAndNameDto> getMapIdAndNameWithFilterAndPagination() {
        StringBuilder queryString = new StringBuilder(
                "Select New nsv.com.nsvserver.Dto.MapWithIdAndNameDto(m.id, m.name) FROM Map m left join m.warehouse WHERE m.warehouse IS NULL"
        );



        Query query = entityManager.createQuery(queryString.toString());


        List<MapWithIdAndNameDto> resultList= query.getResultList();

        return resultList;

    }
}

package nsv.com.nsvserver.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import nsv.com.nsvserver.Dto.PageDto;
import nsv.com.nsvserver.Dto.SearchPartnerDto;
import nsv.com.nsvserver.Entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PartnerDaoImpl implements PartnerDao{
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public PartnerDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<SearchPartnerDto> searchWithFilterAndPagination(Integer page, Integer pageSize, String name, String phone) {
        StringBuilder queryString = new StringBuilder(
                "Select New nsv.com.nsvserver.Dto.SearchPartnerDto(p.id, profile.name,profile.phone,profile.) FROM Partner p join p.profile as profile WHERE 1=1"
                );

        if(name!=null){
            queryString.append(" AND profile.name LIKE:namePattern");

        }
        if(phone!=null){
            queryString.append(" AND profile.phoneNumber = :phone");

        }
        Query query = entityManager.createQuery(queryString.toString());

        if(name!=null){
            query.setParameter("namePattern","%"+name+"%");

        }
        if(phone!=null){
            query.setParameter("phone",phone);

        }
        List<SearchPartnerDto> resultList= query.setFirstResult((page-1)*pageSize)
                .setMaxResults(pageSize)
                .getResultList();
        return resultList;
    }

    @Override
    public long countSearchWithFilter(String name, String phone ) {
        StringBuilder countQueryString = new StringBuilder(
                "Select COUNT(p.id) from Partner p join p.profile profile WHERE 1=1"
        );

        if(name!=null){
            countQueryString.append(" AND profile.name LIKE :namePattern");
        }
        if(phone!=null){
            countQueryString.append(" AND profile.phoneNumber = :phone");
        }

        Query countQuery = entityManager.createQuery(countQueryString.toString(), Long.class);

        if(name!=null){

            countQuery.setParameter("namePattern","%"+name+"%");
        }

        if(phone!=null){
            countQuery.setParameter("phone",phone);
        }

        Long count = (Long) countQuery.getSingleResult();
        return count;
    }
}

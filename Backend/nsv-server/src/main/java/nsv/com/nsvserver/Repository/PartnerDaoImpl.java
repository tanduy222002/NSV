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
                "Select New nsv.com.nsvserver.Dto.SearchPartnerDto(p.id, profile.name,profile.phoneNumber, a) FROM Partner p " +
                        "join p.profile as profile join profile.address as a join fetch a.ward as w join fetch w.district as d join fetch d.province WHERE 1=1"
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

    @Override
    public List<SearchPartnerDto> getStatisticWithFilterAndPagination(Integer pageIndex, Integer pageSize, String name, String phone) {
        StringBuilder queryString = new StringBuilder(
                "Select New nsv.com.nsvserver.Dto.PartnerWithStatisticDto(p.id, profile.name, profile.phoneNumber, a, SUM(debt.amount), SUM(b.weight*b.price) ) FROM Partner p " +
                        "left join p.profile as profile left join profile.address as a left join fetch a.ward as w left join fetch w.district as d left join fetch d.province " +
                        "left join p.transferTickets as tt left join tt.debt as debt left join tt.bins as b WHERE (tt.status IS NULL or tt.status='APPROVED')  "


        );

        if(name!=null){
            queryString.append(" AND profile.name LIKE:namePattern");

        }
        if(phone!=null){
            queryString.append(" AND profile.phoneNumber = :phone");

        }
        queryString.append(" GROUP BY p.id,profile.name,profile.phoneNumber,a");
        Query query = entityManager.createQuery(queryString.toString());

        if(name!=null){
            query.setParameter("namePattern","%"+name+"%");

        }
        if(phone!=null){
            query.setParameter("phone",phone);

        }
        List<SearchPartnerDto> resultList= query.setFirstResult((pageIndex-1)*pageSize)
                .setMaxResults(pageSize)
                .getResultList();
        return resultList;
    }

    @Override
    public long countGetStatisticWithFilter(String name, String phone) {
        return 0;
    }
}

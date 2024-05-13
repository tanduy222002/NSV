package nsv.com.nsvserver.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import nsv.com.nsvserver.Entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProfileDaoImpl implements ProfileDao{
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public ProfileDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Profile> findAllWithEagerLoading(Integer page, Integer pageSize, String name, String status){
        StringBuilder queryString = new StringBuilder("select p from Profile p join fetch p.employee e join fetch e.roles as roles" +
                " left join fetch e.otp left join fetch e.refreshToken left join fetch p.address a " +
                "left join fetch a.ward w left join fetch w.district d left join fetch d.province pv WHERE 1=1");
        if(name!=null){
            queryString.append(" AND p.name LIKE :name");
        }
        if(status!=null){
            queryString.append(" AND e.status = :status");
        }

        Query query = entityManager.createQuery(queryString.toString());

        if(name!=null){
            query.setParameter("name","%"+name+"%");
        }
        if(status!=null){
            query.setParameter("status",status);
        }
        List<Profile> resultList =query
                .setFirstResult((page-1)*pageSize)
                .setMaxResults(pageSize)
                .getResultList();
        return resultList;
    }

    @Override
    public Optional<Profile> findOneWithEagerLoading(Integer id){
        String queryString = "select p from Profile p join fetch p.employee e " +
                " left join fetch p.address a left join fetch a.ward w left join fetch w.district d left join fetch d.province pv" +
                " WHERE e.id=:id";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("id",id);
        List<Profile> profiles = query.getResultList();

        if (profiles.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(profiles.get(0));


    }
    public long getTotalCount() {
        long totalCount = entityManager.createQuery("select count(p) from Profile p join p.employee", Long.class).getSingleResult();
        return totalCount;

    }
}

package nsv.com.nsvserver.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import nsv.com.nsvserver.Entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProfileDaoImpl implements ProfileDao{
    EntityManager entityManager;

    @Autowired
    public ProfileDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<Profile> findAllWithEagerLoading(Integer page, Integer pageSize){
        List<Profile> resultList = entityManager.createQuery("select p from Profile p join fetch p.employee e left join fetch p.address a left join fetch a.ward w left join fetch w.district d left join fetch d.province pv")
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

package nsv.com.nsvserver.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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


//    @Override
//    public List<Profile> searchWithFilterAndPagination(Integer page, Integer pageSize) {
//        StringBuilder query = new StringBuilder();
//        query.append("Select p from Partner p join fetch p.profile profile where ");
//        entityManager.createQuery("select p from Partner p join fetch p.profile e left join fetch p.address a left join fetch a.ward w left join fetch w.district d left join fetch d.province pv")
//                .setFirstResult((page-1)*pageSize)
//                .setMaxResults(pageSize)
//                .getResultList();
//    }

    @Override
    public long getTotalCount() {
        return 0;
    }
}

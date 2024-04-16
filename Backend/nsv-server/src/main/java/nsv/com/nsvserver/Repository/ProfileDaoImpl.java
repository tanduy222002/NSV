package nsv.com.nsvserver.Repository;

import jakarta.persistence.EntityManager;
import nsv.com.nsvserver.Dto.EmployeeDto;
import nsv.com.nsvserver.Dto.PageDto;
import nsv.com.nsvserver.Entity.Employee;
import nsv.com.nsvserver.Entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

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
    public long getTotalCount() {
        long totalCount = entityManager.createQuery("select count(p) from Profile p", Long.class).getSingleResult();
        return totalCount;

    }
}

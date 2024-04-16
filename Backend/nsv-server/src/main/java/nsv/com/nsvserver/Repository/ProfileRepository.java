package nsv.com.nsvserver.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nsv.com.nsvserver.Entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Integer> {

    public Profile save(Profile profile);
    @Query("select p from Profile p join fetch p.employee e join fetch p.address a join fetch a.ward w join fetch w.district d join fetch d.province pv")
    public List<Profile> findAllWithEagerLoading(Integer page, Integer pageSize);


}

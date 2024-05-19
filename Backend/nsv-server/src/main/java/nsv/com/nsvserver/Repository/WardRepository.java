package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.District;
import nsv.com.nsvserver.Entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward,Integer> {
    @Query("select new nsv.com.nsvserver.Entity.Ward(w.id, w.name) from Ward w join w.district d Where d.id=:id")
    public List<Ward> findWardsInDistrict(@Param("id") Integer id);

    @Query("select new nsv.com.nsvserver.Entity.Ward(w.id, w.name) from Ward w where w.id=:id")
    public Ward findWardIdAndNameById(@Param("id") Integer id);
}

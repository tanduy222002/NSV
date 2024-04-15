package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.District;
import nsv.com.nsvserver.Entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DistrictRepository extends JpaRepository<District,Integer> {
    @Query("select new nsv.com.nsvserver.Entity.District(d.id, d.name) from District d Where d.id=:id")
    public District findDistrictNameAndId(@Param("id") Integer id);

    @Query("select new nsv.com.nsvserver.Entity.District(d.id, d.name) from District d join d.province p Where p.id=:id")
    public List<District> findDistrictsInProvince(@Param("id") Integer id);
}


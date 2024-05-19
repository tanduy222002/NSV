package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Dto.ProvinceResponseDto;
import nsv.com.nsvserver.Entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
    public interface ProvinceRepository extends JpaRepository<Province,Integer> {





    @Query("select new nsv.com.nsvserver.Entity.Province(p.id, p.name) from Province p")
    public List<Province> findAllIdAndName();

    @Query("select new nsv.com.nsvserver.Entity.Province(p.id, p.name) from Province p Where p.id=:id")
    public Province findProvinceNameAndIdById(@Param("id") Integer id);

    public boolean existsByName(String name);
    }


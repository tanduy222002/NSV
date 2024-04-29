package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Integer> {
    @Query("select new nsv.com.nsvserver.Dto.WarehouseNameAndIdDto(w.id, w.name) from Warehouse w")
    public List<?> getWarehouseNameAndId();
}

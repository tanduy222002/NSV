package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Dto.WarehouseSlotsDto;
import nsv.com.nsvserver.Entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Integer> {
    @Query("select new nsv.com.nsvserver.Dto.WarehouseNameAndIdDto(w.id, w.name, w.lowerTemperature, w.upperTemperature) from Warehouse w")
    public List<?> getWarehouseNameAndId();

    @Query("select new nsv.com.nsvserver.Dto.WarehouseNameAndIdDto(w.id, w.name, w.lowerTemperature, w.upperTemperature) from Warehouse w WHERE w.lowerTemperature<= :minTem AND w.upperTemperature>=:maxTem")
    public List<?> getWarehouseSuitableForProductTemperature(@Param("minTem") Double minTem, @Param("maxTem") Double maxTemp);

    @Query("select new nsv.com.nsvserver.Dto.WarehouseSlotsDto(s.id, s.name, s.capacity, s.containing) from Warehouse w " +
            "join w.map as m " +
            "join m.rows as r " +
            "join r.slots as s " +
            "where w.id = :id")
    public List<WarehouseSlotsDto> getWarehouseSlot(@Param("id") Integer id);
}

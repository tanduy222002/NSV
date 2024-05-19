package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MapRepository extends JpaRepository<Map,Integer> {

    @Query(value = "FROM Map m JOIN m.rows as r JOIN r.slots WHERE m.id = :id")
    Optional<Map> findById(Integer id);
    boolean existsByName(String name);
}

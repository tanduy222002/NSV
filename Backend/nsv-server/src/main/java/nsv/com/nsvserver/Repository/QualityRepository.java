package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.Product;
import nsv.com.nsvserver.Entity.Quality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QualityRepository extends JpaRepository<Quality, Integer> {

}

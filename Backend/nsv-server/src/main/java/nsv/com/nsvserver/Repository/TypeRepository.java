package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.Quality;
import nsv.com.nsvserver.Entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {

}

package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Annotation.TraceTime;
import nsv.com.nsvserver.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @TraceTime
    public Role findByName(String name);
}

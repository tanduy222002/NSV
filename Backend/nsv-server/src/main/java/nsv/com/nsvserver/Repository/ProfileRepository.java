package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Integer> {
    public Profile save(Profile profile);
}

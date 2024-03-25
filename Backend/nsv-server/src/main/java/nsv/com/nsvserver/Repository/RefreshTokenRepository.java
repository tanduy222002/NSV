package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.Employee;
import nsv.com.nsvserver.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByEmployee(Employee employee);


}

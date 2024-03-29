package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp,Integer> {
    public Optional<Otp> findByCode(String code);
}

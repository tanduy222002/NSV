package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.Product;
import nsv.com.nsvserver.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    public List<Product> findAll();
}

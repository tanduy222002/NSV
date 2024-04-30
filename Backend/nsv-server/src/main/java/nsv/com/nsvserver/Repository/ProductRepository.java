package nsv.com.nsvserver.Repository;

import jakarta.persistence.criteria.Predicate;
import nsv.com.nsvserver.Dto.ProductDto;
import nsv.com.nsvserver.Entity.Product;
import nsv.com.nsvserver.Entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    boolean existsByName(String name);

    @Query("From Product p join p.types as t join t.qualities WHERE p.id=:productId")
    Optional<Product> findWithEagerQualityAndType(@Param("productId") Integer productId);
}

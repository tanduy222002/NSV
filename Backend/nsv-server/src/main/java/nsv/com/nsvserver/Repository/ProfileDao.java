package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Dto.PageDto;
import nsv.com.nsvserver.Entity.Profile;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProfileDao {

    public List<Profile> findAllWithEagerLoading(Integer page, Integer pageSize,String name, String status);

    Optional<Profile> findOneWithEagerLoading(Integer id);

    public long getTotalCount();

    public long getTotalCountWithFilerAndPagination(String name, String status);
}

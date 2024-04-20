package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Dto.SearchPartnerDto;
import nsv.com.nsvserver.Entity.Profile;

import java.util.List;

public interface PartnerDao {

    public List<SearchPartnerDto> searchWithFilterAndPagination(Integer page, Integer pageSize, String name, String phone);

    public long countSearchWithFilter(String name, String phone);

}

package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Dto.PartnerDetailDto;
import nsv.com.nsvserver.Dto.SearchPartnerDto;
import nsv.com.nsvserver.Entity.Profile;

import java.util.List;

public interface PartnerDao {

    public List<SearchPartnerDto> searchWithFilterAndPagination(Integer page, Integer pageSize, String name, String phone);

    public long countSearchWithFilter(String name, String phone);

    List<SearchPartnerDto> getStatisticWithFilterAndPagination(Integer pageIndex, Integer pageSize, String name, String phone);

    PartnerDetailDto getPartnerDetailById(Integer id);

    long countGetStatisticWithFilter(String name, String phone);

    public List<?> getTransactionsOfPartnerById(Integer id, Integer pageIndex, Integer pageSize);

}

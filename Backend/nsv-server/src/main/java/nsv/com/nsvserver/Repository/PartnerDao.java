package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Dto.*;
import nsv.com.nsvserver.Entity.Profile;

import java.util.List;

public interface PartnerDao {

    public List<SearchPartnerDto> searchWithFilterAndPagination(Integer page, Integer pageSize, String name, String phone);

    public long countSearchWithFilter(String name, String phone);

    List<SearchPartnerDto> getStatisticWithFilterAndPagination(Integer pageIndex, Integer pageSize, String name, String phone);

    PartnerDetailDto getPartnerDetailById(Integer id);

    long countGetStatisticWithFilter(String name, String phone);

    public List<TransferTicketDto> getTransactionsOfPartnerById(Integer pageIndex, Integer pageSize,Integer id,  String name, Boolean isPaid);

    long countTransactionsOfPartnerById(Integer pageIndex, Integer pageSize, Integer id, String name, Boolean isPaid);

    List<DebtDetailDto> getDebtsOfPartnerById(Integer pageIndex, Integer pageSize, Integer partnerId, Boolean isPaid);

    long countDebtsOfPartnerById(Integer pageIndex, Integer pageSize, Integer partnerId, Boolean isPaid);
}

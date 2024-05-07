package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.Bin;
import nsv.com.nsvserver.Entity.BinBin;
import nsv.com.nsvserver.Entity.TransferTicket;

import java.util.List;

public interface TicketDao {

    public TransferTicket fetchWithBinAndSlot(Integer Id);

    List<TransferTicket> getTicketWithFilterAndPagination(Integer pageIndex, Integer pageSize, String name, String type, String status);

    long countTotalTicketWithFilterAndPagination(Integer pageIndex, Integer pageSize, String name, String type, String status);

    TransferTicket fetchExportTicket(Integer Id);

    List<BinBin> fetchBinBinByExportBinId(Integer Id);

    List<Bin> getImportBinInTicketDetail(Integer Id);

    TransferTicket getTicketDetail(Integer Id);
}

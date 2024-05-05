package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.TransferTicket;

import java.util.List;

public interface TicketDao {

    public TransferTicket fetchWithBinAndSlot(Integer Id);

    List<TransferTicket> getTicketWithFilterAndPagination(Integer pageIndex, Integer pageSize, String name, String type, String status);

    long countTotalTicketWithFilterAndPagination(Integer pageIndex, Integer pageSize, String name, String type, String status);

    TransferTicket fetchExportTicket(Integer Id);
}

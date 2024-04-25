package nsv.com.nsvserver.Repository;

import nsv.com.nsvserver.Entity.Profile;
import nsv.com.nsvserver.Entity.TransferTicket;

import java.util.List;

public interface TicketDao {

    public TransferTicket fetchWithBinAndSlot(Integer Id);

}

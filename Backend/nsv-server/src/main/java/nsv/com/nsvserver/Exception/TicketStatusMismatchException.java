package nsv.com.nsvserver.Exception;

public class TicketStatusMismatchException extends RuntimeException{
    public TicketStatusMismatchException() {
        super("Ticket status is not match");
    }

    public TicketStatusMismatchException(String message) {
        super(message);
    }
}

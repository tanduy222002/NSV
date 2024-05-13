package nsv.com.nsvserver.Exception;


public class AccountSuspendedException extends RuntimeException{
    public AccountSuspendedException() {
        super("This account is suspended");
    }

    public AccountSuspendedException(String message) {
        super(message);
    }
}

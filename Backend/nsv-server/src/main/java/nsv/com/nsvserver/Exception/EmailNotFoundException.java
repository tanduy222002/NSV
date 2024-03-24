package nsv.com.nsvserver.Exception;

public class EmailNotFoundException extends IllegalStateException{
    public EmailNotFoundException() {
        super("Email not found exception");
    }

    public EmailNotFoundException(String message) {
        super(message);
    }
}

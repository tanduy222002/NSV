package nsv.com.nsvserver.Exception;

public class UserNameExistsException extends IllegalStateException {
    public UserNameExistsException() {
        super("Username already exists");
    }
    public UserNameExistsException(String message) {
        super(message);
    }

}

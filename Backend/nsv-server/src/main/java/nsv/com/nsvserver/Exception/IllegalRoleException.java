package nsv.com.nsvserver.Exception;

public class IllegalRoleException extends IllegalStateException{
    public IllegalRoleException() {
        super("Username already exists");
    }
    public IllegalRoleException(String message) {
        super(message);
    }
}

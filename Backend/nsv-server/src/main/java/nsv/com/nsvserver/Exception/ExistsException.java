package nsv.com.nsvserver.Exception;

import org.springframework.dao.DataIntegrityViolationException;

public class ExistsException extends DataIntegrityViolationException {
    public ExistsException() {
        super("Entity already exists");
    }
    public ExistsException(String message) {
        super(message);
    }

}

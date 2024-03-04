package nsv.com.nsvserver.Exception;

import org.springframework.dao.DataIntegrityViolationException;

public class ProductExistsException extends DataIntegrityViolationException {
    public ProductExistsException() {
        super("Product already exists");
    }
    public ProductExistsException(String message) {
        super(message);
    }

}

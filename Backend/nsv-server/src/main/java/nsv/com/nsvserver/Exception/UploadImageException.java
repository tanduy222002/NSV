package nsv.com.nsvserver.Exception;

import java.io.IOException;

public class UploadImageException extends RuntimeException {
    public UploadImageException() {
        super("Error when uploading image");
    }

    public UploadImageException(String message) {
        super(message);
    }
}

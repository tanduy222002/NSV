package nsv.com.nsvserver.Exception;

import java.io.IOException;

public class UploadImageException extends IOException {
    public UploadImageException() {
        super("Error when uploading image");
    }

    public UploadImageException(String message) {
        super(message);
    }
}

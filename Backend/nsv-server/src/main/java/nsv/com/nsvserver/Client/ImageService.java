package nsv.com.nsvserver.Client;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    void upLoadImageWithFile(MultipartFile file) throws IOException;

    String upLoadImageWithBase64(String code) throws IOException;
    void deleteImage(String id);
}

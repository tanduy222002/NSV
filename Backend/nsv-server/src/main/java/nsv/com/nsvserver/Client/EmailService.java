package nsv.com.nsvserver.Client;

import nsv.com.nsvserver.Dto.EmailDetailDto;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    void sendEmail(EmailDetailDto emailDetailDto);
    void SendEmailWithAttachments(EmailDetailDto emailDetailDto);

    void sendEmailWithHtmlContent(EmailDetailDto emailDetailDto);

}

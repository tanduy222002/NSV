package nsv.com.nsvserver.Client;

import nsv.com.nsvserver.Dto.EmailDetailDto;

public interface EmailService {
    void sendEmail(EmailDetailDto emailDetailDto);
    void SendEmailWithAttachments(EmailDetailDto emailDetailDto);

    void sendEmailWithHtmlContent(EmailDetailDto emailDetailDto);
}

package nsv.com.nsvserver.Client;

import nsv.com.nsvserver.Dto.EmailDetail;

public interface EmailService {
    void sendEmail(EmailDetail emailDetail);
    void SendEmailWithAttachments(EmailDetail emailDetail);

    void sendEmailWithHtmlContent(EmailDetail emailDetail);
}

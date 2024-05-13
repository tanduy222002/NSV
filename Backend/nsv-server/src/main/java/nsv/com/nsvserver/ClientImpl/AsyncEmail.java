package nsv.com.nsvserver.ClientImpl;

import nsv.com.nsvserver.Client.EmailService;
import nsv.com.nsvserver.Dto.EmailDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncEmail {
    @Autowired
    private EmailService emailService;
    @Async
    public void sendAsyncEmail(EmailDetailDto emailDetailDto){
        emailService.sendEmailWithHtmlContent(emailDetailDto);
    }
}

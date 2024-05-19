package nsv.com.nsvserver.ClientImpl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import nsv.com.nsvserver.Client.EmailService;
import nsv.com.nsvserver.Dto.EmailDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

import static org.antlr.v4.runtime.misc.Utils.readFile;


@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;


    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }



    @Override
    public void sendEmail(EmailDetailDto emailDetailDto) {
        try{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("duytan222002@gmail.com");
        message.setTo(emailDetailDto.getRecipient());
        message.setSubject(emailDetailDto.getSubject());
        message.setText(emailDetailDto.getMsgBody());
        mailSender.send(message);}
        catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void sendEmailWithHtmlContent(EmailDetailDto emailDetailDto) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");

        try {
            mimeMessageHelper.setFrom("duytan222002@gmail.com");
            mimeMessageHelper.setTo(emailDetailDto.getRecipient());
            mimeMessageHelper.setSubject(
                    emailDetailDto.getSubject());

            mimeMessageHelper.setText(emailDetailDto.getMsgBody(),true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void SendEmailWithAttachments(EmailDetailDto emailDetailDto) {
        MimeMessage mimeMessage
                = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("duytan222002@gmail.com");
            mimeMessageHelper.setTo(emailDetailDto.getRecipient());
            mimeMessageHelper.setText(emailDetailDto.getMsgBody());
            mimeMessageHelper.setSubject(
                    emailDetailDto.getSubject());

            // Adding the attachment
            FileSystemResource file
                    = new FileSystemResource(
                    new File(emailDetailDto.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);

            // Sending the mail
            mailSender.send(mimeMessage);
            System.out.println("Mail sent Successfully");
        }

      catch (MessagingException e) {
            throw new RuntimeException(e);
        }




    }



}



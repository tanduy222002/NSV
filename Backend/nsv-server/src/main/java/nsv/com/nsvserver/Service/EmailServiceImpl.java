package nsv.com.nsvserver.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import nsv.com.nsvserver.Client.EmailService;
import nsv.com.nsvserver.Dto.EmailDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.antlr.v4.runtime.misc.Utils.readFile;


@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;


    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(EmailDetail emailDetail) {
        try{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("duytan222002@gmail.com");
        message.setTo(emailDetail.getRecipient());
        message.setSubject(emailDetail.getSubject());
        message.setText(emailDetail.getMsgBody());
        mailSender.send(message);}
        catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void sendEmailWithHtmlContent(EmailDetail emailDetail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");

        try {
            mimeMessageHelper.setFrom("tanduy222002@gmail.com");
            mimeMessageHelper.setTo(emailDetail.getRecipient());
            mimeMessageHelper.setSubject(
                    emailDetail.getSubject());

            mimeMessageHelper.setText(emailDetail.getMsgBody(),true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void SendEmailWithAttachments(EmailDetail emailDetail) {
        MimeMessage mimeMessage
                = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("tanduy222002@gmail.com");
            mimeMessageHelper.setTo(emailDetail.getRecipient());
            mimeMessageHelper.setText(emailDetail.getMsgBody());
            mimeMessageHelper.setSubject(
                    emailDetail.getSubject());

            // Adding the attachment
            FileSystemResource file
                    = new FileSystemResource(
                    new File(emailDetail.getAttachment()));

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



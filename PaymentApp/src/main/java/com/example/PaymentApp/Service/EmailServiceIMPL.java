package com.example.PaymentApp.Service;

import com.example.PaymentApp.DTO.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceIMPL implements EmailService {

@Autowired
private JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    private String sender;



    @Override
    public String sendSimpleMail(EmailSender emaildetails) {

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setText("Your account has been frozen due to Unseen Circumstances, Please contact the Admin team for more support");
            mailMessage.setTo(emaildetails.getRecipient());
            System.out.println(emaildetails.getRecipient());
            mailMessage.setSubject("Your account has been Frozen");
            javaMailSender.send(mailMessage);

            return "Mail sent Succesfully";
        } catch (Exception e) {
            return "Error during sending the mail";
        }
    }

}

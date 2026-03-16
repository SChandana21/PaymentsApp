package com.example.PaymentApp.Service;

import com.example.PaymentApp.MailSender.EmailSender;

public interface EmailService {

    public String sendSimpleMail(EmailSender emaildetails);
}

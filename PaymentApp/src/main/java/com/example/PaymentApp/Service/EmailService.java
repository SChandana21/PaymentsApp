package com.example.PaymentApp.Service;

import com.example.PaymentApp.DTO.EmailSender;

public interface EmailService {

    public String sendSimpleMail(EmailSender emaildetails);
}

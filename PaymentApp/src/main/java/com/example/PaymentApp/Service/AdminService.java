package com.example.PaymentApp.Service;


import com.example.PaymentApp.Entity.Transactions;
import com.example.PaymentApp.Entity.User;
import com.example.PaymentApp.Entity.Wallet;
import com.example.PaymentApp.DTO.EmailSender;
import com.example.PaymentApp.Repositories.TransactionRepo;
import com.example.PaymentApp.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private EmailServiceIMPL emailServiceIMPL;

    @Autowired
    private UserRepo userRepo;


    @Autowired
    private TransactionRepo transactionRepo;


        @Transactional
        public boolean FreezeUser(EmailSender usertofreeze) {
            try {
                String recipient = usertofreeze.getRecipient();
                User user = userRepo.findByuserEmail(recipient);
                Wallet userwallet = user.getWallet().getFirst();
                userwallet.setActive(false);
                if (userwallet != null) {
                    emailServiceIMPL.sendSimpleMail(usertofreeze);
                }
            } catch (Exception e) {
                return  false;
            }
            return true;
        }


        public List<Transactions> GetallTransactions() {
            List<Transactions> all = transactionRepo.findAll();
            return all;

        }





}

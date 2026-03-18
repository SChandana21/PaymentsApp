package com.example.PaymentApp.Service;


import com.example.PaymentApp.Entity.Transactions;
import com.example.PaymentApp.Entity.User;
import com.example.PaymentApp.Entity.Wallet;
import com.example.PaymentApp.DTO.EmailSender;
import com.example.PaymentApp.Repositories.TransactionRepo;
import com.example.PaymentApp.Repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class AdminService {

    @Autowired
    private EmailServiceIMPL emailServiceIMPL;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Transactional
    public boolean UnFreezeUser(EmailSender usertofreeze) {
        try {
            String recipient = usertofreeze.getRecipient();
            User user = userRepo.findByuserEmail(recipient);
            Wallet userwallet = user.getWallet().getFirst();
            userwallet.setActive(true);
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

    public void SignupAdmin(User newadmin) {
            try {
                newadmin.setPassword(passwordEncoder.encode(newadmin.getPassword()));
                newadmin.setRoles(Arrays.asList("ADMIN"));
                userRepo.save(newadmin);
            } catch (Exception e) {
                log.error("Unable to create Admin!");
            }
    }





}

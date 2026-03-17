package com.example.PaymentApp.Service;


import com.example.PaymentApp.Entity.User;
import com.example.PaymentApp.Entity.Wallet;
import com.example.PaymentApp.DTO.EmailSender;
import com.example.PaymentApp.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminService {

    @Autowired
    private EmailServiceIMPL emailServiceIMPL;

    @Autowired
    private UserRepo userRepo;


    @GetMapping("/me")
    public String me() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }


    @PostMapping("/freeze")
    public void FreezeuserWallet(@RequestBody EmailSender usertofreeze) {
        String recipient = usertofreeze.getRecipient();
        User user = userRepo.findByuserEmail(recipient);
        Wallet userwallet = user.getWallet().getFirst();
        userwallet.setActive(false);
        if (userwallet != null) {
            emailServiceIMPL.sendSimpleMail(usertofreeze);
        }
    }





}

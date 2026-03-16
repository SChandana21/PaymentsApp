package com.example.PaymentApp.Service;


import com.example.PaymentApp.Entity.User;
import com.example.PaymentApp.Entity.Wallet;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminService {

    @GetMapping("/me")
    public String me() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    @GetMapping("/test")
    public void VerifyAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            System.out.println(authority);
        }
    }

    @PostMapping
    public void FreezeuserWallet(User UsertoFreeze) {           //authorized request
        //email to send to          //admin logs
        Wallet userwallet = UsertoFreeze.getWallet().get(0);        //change to query?
        userwallet.setActive(false);

    }


}

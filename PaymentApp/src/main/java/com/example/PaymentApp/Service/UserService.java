package com.example.PaymentApp.Service;

import com.example.PaymentApp.Entity.User;
import com.example.PaymentApp.Entity.Wallet;
import com.example.PaymentApp.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WalletService walletService;

    public void Signup(User newuser) {
         newuser.setPassword(passwordEncoder.encode(newuser.getPassword()));
         walletService.InitializeWallet(newuser);
         userRepo.save(newuser);

    }

}

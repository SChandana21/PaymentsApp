package com.example.PaymentApp.Service;

import com.example.PaymentApp.Entity.User;
import com.example.PaymentApp.Entity.Wallet;
import com.example.PaymentApp.Repositories.UserRepo;
import com.example.PaymentApp.Repositories.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WalletService {
    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private UserRepo userRepo;

    public void InitializeWallet(User newuser) {
        Wallet wallet = new Wallet();
        wallet.setBalance(0f);
        walletRepo.save(wallet);
        if (newuser.getWallet() == null) {
            newuser.setWallet(new ArrayList<>());
        }
        newuser.getWallet().add(wallet);
    }

}



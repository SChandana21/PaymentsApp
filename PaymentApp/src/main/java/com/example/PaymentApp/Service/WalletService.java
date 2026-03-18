package com.example.PaymentApp.Service;

import com.example.PaymentApp.Entity.User;
import com.example.PaymentApp.Entity.Wallet;
import com.example.PaymentApp.Repositories.UserRepo;
import com.example.PaymentApp.Repositories.WalletRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<Wallet> Ipuserwallet()  {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null
                || !auth.isAuthenticated()
                || auth instanceof AnonymousAuthenticationToken) {

            throw new RuntimeException("User not authenticated");
        }
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User userf = userRepo.findByuserName(username);
        if (userf == null) {
            throw new RuntimeException("User not found");
        }
            List<Wallet> wallet = userf.getWallet();
            Optional<Wallet> userwallet = null;
            if (!wallet.isEmpty()) {
                ObjectId walletID = wallet.get(0).getId();
                System.out.println(walletID);
                userwallet = walletRepo.findById(walletID);
                System.out.println(userwallet);
            }
            return userwallet;

    }

    public float GetBalance() {
        float balance = -1;
        Wallet userwallet = Ipuserwallet().orElse(null);
        if (userwallet != null) {
            balance = userwallet.getBalance();
            System.out.println(balance);
        }
        return balance;
    }
    @Transactional
    public void Deposit (float Amounttodeposit) {
        Wallet userwallet = Ipuserwallet().orElse(null);
        float Currentbalance = GetBalance();
        if (userwallet != null && Amounttodeposit > 0 ) {
            Currentbalance += Amounttodeposit;
            userwallet.setBalance(Currentbalance);
            walletRepo.save(userwallet);
        }

    }


    @Transactional
    public void Withdraw(float Amounttowithdraw) {
        Wallet userwallet = Ipuserwallet().orElse(null);
        if (userwallet != null) {
            float Currentbalance = GetBalance();
            if (Currentbalance >= Amounttowithdraw) {
                Currentbalance -= Amounttowithdraw;
                userwallet.setBalance(Currentbalance);
                walletRepo.save(userwallet);
            } else {
                System.out.println("error");
            }
        }
    }





}




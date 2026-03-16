package com.example.PaymentApp.Controllers;

import com.example.PaymentApp.Entity.User;
import com.example.PaymentApp.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping
    public ResponseEntity<?> CheckBalance() {
        float balancefound = walletService.GetBalance();
        return new ResponseEntity<>("Hi" +  "Your balance is" + balancefound, HttpStatus.FOUND);
    }

    @PostMapping("/Deposit")
    public ResponseEntity<?> DepositMoney(@RequestBody float Moneytodeposit) {
        //email
        walletService.Deposit(Moneytodeposit);
        float currentbalance = walletService.GetBalance();
        return new ResponseEntity<>(currentbalance, HttpStatus.CREATED);
    }
        //wrong http codes

    @PostMapping("/withdraw")
    public ResponseEntity<?> Withdrawmoney(@RequestBody float Moneytowithdraw) {                //logs
        walletService.Withdraw(Moneytowithdraw);
        float currentbalance = walletService.GetBalance();
        return new ResponseEntity<>(currentbalance, HttpStatus.ACCEPTED);

    }
}
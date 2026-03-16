package com.example.PaymentApp.Service;

import com.example.PaymentApp.Entity.User;
import com.example.PaymentApp.Entity.Wallet;
import com.example.PaymentApp.Repositories.UserRepo;
import com.example.PaymentApp.Repositories.WalletRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SendMoneyService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private WalletService  walletService;

    @Transactional
    public boolean SendMoney(float Amounttosend, String Recieveremail) {
        //sender wallet
        boolean Succesfultransaction  = false;
        float Mybalance = walletService.GetBalance();
        User Reciever = userRepo.findByuserEmail(Recieveremail);
        if (Mybalance > Amounttosend && Reciever != null) {
            List<Wallet> wallet = Reciever.getWallet();
            System.out.println(wallet);
            Wallet recieverwallet = wallet.getFirst();
            if (recieverwallet != null && recieverwallet.isActive()) {
                float recievercurrentbalance = recieverwallet.getBalance();
                recieverwallet.setBalance(
                        recievercurrentbalance += Amounttosend
                );
                Mybalance = Mybalance - Amounttosend;
                Wallet mywallet = walletService.Ipuserwallet().orElse(null);
                if (mywallet != null) {
                    mywallet.setBalance(Mybalance);
                }
                walletRepo.save(recieverwallet);
                walletRepo.save(mywallet);
                Succesfultransaction = true;//email and logs pending (6)
            }
            Succesfultransaction = false;
        }
        return Succesfultransaction;

    }



}

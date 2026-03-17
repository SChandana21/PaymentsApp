package com.example.PaymentApp.Service;

import com.example.PaymentApp.Entity.Transactions;
import com.example.PaymentApp.Entity.User;
import com.example.PaymentApp.Entity.Wallet;
import com.example.PaymentApp.Repositories.TransactionRepo;
import com.example.PaymentApp.Repositories.UserRepo;
import com.example.PaymentApp.Repositories.WalletRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SendMoneyService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private WalletService  walletService;


    @Autowired
    private TransactionRepo transactionRepo;



    @Transactional
    public boolean SendMoney(float Amounttosend, String Recieveremail) {
        //sender wallet
        float Mybalance = 0;
        boolean Succesfultransaction  = false;
        Wallet mywallet = walletService.Ipuserwallet().orElse(null);
        if (mywallet != null) {
            Mybalance = mywallet.getBalance();
        }
        User Reciever = userRepo.findByuserEmail(Recieveremail);
        if ((Mybalance > Amounttosend) && (Reciever != null) && mywallet.isActive()) {
            List<Wallet> wallet = Reciever.getWallet();
            System.out.println(wallet);
            Wallet recieverwallet = wallet.getFirst();
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            User sender = userRepo.findByuserName(name);
            if (recieverwallet != null && recieverwallet.isActive()) {
                float recievercurrentbalance = recieverwallet.getBalance();
                recieverwallet.setBalance(
                        recievercurrentbalance += Amounttosend
                );
                Mybalance = Mybalance - Amounttosend;
                if (mywallet != null) {
                    mywallet.setBalance(Mybalance);
                }
                walletRepo.save(recieverwallet);
                walletRepo.save(mywallet);
                Transactions recievertransactions = new Transactions();
                recievertransactions.setAmountsent(Amounttosend);
                recievertransactions.setDatetimeattransaction(LocalDateTime.now());
                recievertransactions.setTransactionType("Credited");
                recievertransactions.setSenderEmail(sender.getUserEmail());
                transactionRepo.save(recievertransactions);
                if (Reciever.getTransactions() == null) {
                    Reciever.setTransactions(new ArrayList <>());
                }
                Reciever.getTransactions().add(recievertransactions);
                userRepo.save(Reciever);
                Transactions senderTransaction = new Transactions();
                senderTransaction.setAmountsent(Amounttosend);
                senderTransaction.setDatetimeattransaction(LocalDateTime.now());
                senderTransaction.setTransactionType("Debited");
                senderTransaction.setRecieveremai(Recieveremail);
                transactionRepo.save(senderTransaction);
                if (sender.getTransactions() == null) {
                    sender.setTransactions(new ArrayList <>());
                }
                sender.getTransactions().add(senderTransaction);
                userRepo.save(sender);

                Succesfultransaction = true;//email and logs pending (6)
            } else {
                Succesfultransaction = false;
            }
            }
        return Succesfultransaction;



    }



}

package com.example.PaymentApp.Controllers;

import com.example.PaymentApp.DTO.TransactionResponseDTO;
import com.example.PaymentApp.Entity.Transactions;
import com.example.PaymentApp.Repositories.UserQueryGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class ViewTransactionController {

    @Autowired
    private UserQueryGetter userQueryGetter;

    @PostMapping("/byAmount")
    public ResponseEntity<List<Transactions>> FindTransactionByAmount(@RequestBody TransactionResponseDTO Transactiondetail) {
        float amounttoLookup = Transactiondetail.getAmounttoLookup();
        List<Transactions> findbyamount = userQueryGetter.Findbyamount(amounttoLookup);
        return new ResponseEntity<>(findbyamount, HttpStatus.FOUND);
    }

}

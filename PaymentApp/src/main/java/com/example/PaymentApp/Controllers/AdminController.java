package com.example.PaymentApp.Controllers;

import com.example.PaymentApp.DTO.EmailSender;
import com.example.PaymentApp.Entity.Transactions;
import com.example.PaymentApp.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/freeze")
    public ResponseEntity<?> FreezeuserWallet(@RequestBody EmailSender usertofreeze) {
        boolean userfreezed = adminService.FreezeUser(usertofreeze);
        if (userfreezed)
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }


    @PostMapping("/unfreeze")
    public ResponseEntity<?> UnFreezeuserWallet(@RequestBody EmailSender usertofreeze) {
        boolean userfreezed = adminService.FreezeUser(usertofreeze);
        if (userfreezed)
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping
    public ResponseEntity<List<Transactions>> GetTransactions() {
        List<Transactions> transactions = adminService.GetallTransactions();
        if (transactions != null) {
            return new ResponseEntity<>(transactions, HttpStatus.FOUND);

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}

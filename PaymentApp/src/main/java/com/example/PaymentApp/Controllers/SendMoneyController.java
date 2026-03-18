package com.example.PaymentApp.Controllers;

import com.example.PaymentApp.DTO.TransferRequest;
import com.example.PaymentApp.Entity.User;
import com.example.PaymentApp.Repositories.UserRepo;
import com.example.PaymentApp.Service.SendMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transfer")
public class SendMoneyController {

    @Autowired
    private SendMoneyService sendMoneyService;

    @Autowired
    private UserRepo userRepo;
    @PostMapping
    public ResponseEntity TransferMoney(@RequestBody TransferRequest transferRequest) {
        String currentuser = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByuserName(currentuser);
        String currentuserEmail = user.getUserEmail();
        if (transferRequest.getUserEmail() != currentuserEmail) {
            boolean Successfultransactionn = sendMoneyService.SendMoney(transferRequest.getAmount(), transferRequest.getUserEmail());
            if (Successfultransactionn)
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

}

package com.example.PaymentApp.Controllers;

import com.example.PaymentApp.DTO.TransferRequest;
import com.example.PaymentApp.Service.SendMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transfer")
public class SendMoneyController {

    @Autowired
    private SendMoneyService sendMoneyService;

    @PostMapping
    public ResponseEntity TransferMoney(@RequestBody TransferRequest transferRequest) {
        boolean Successfultransactionn = sendMoneyService.SendMoney(transferRequest.getAmount(), transferRequest.getUserEmail());
        if (Successfultransactionn)
            return  new ResponseEntity<>(HttpStatus.ACCEPTED);
        return  new ResponseEntity<>(HttpStatus.CONFLICT);
    }

}

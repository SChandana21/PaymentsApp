package com.example.PaymentApp.Controllers;

import com.example.PaymentApp.Entity.User;
import com.example.PaymentApp.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@Slf4j
public class PublicController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> SignupController(@RequestBody User Newuser) {
        try {
            userService.Signup(Newuser);
        } catch (Exception e) {
        log.error("Error occured while creating user, Please try again");
            return new ResponseEntity<>( HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

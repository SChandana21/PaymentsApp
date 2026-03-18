package com.example.PaymentApp.Controllers;

import com.example.PaymentApp.Entity.User;
import com.example.PaymentApp.Service.UserDetailsServiceImpl;
import com.example.PaymentApp.Service.UserService;
import com.example.PaymentApp.Util.Jwtutil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private Jwtutil jwtutil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

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

    @PostMapping("/login")      //seperate login from service
    public ResponseEntity<String> Login(@RequestBody User newuser) {
        try {
            System.out.println("POST /user hit");
            String jwt = userService.Login(newuser);
            return new ResponseEntity<>(jwt, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            log.error("Exception occured");
            return new ResponseEntity<>("Incorrect", HttpStatus.BAD_REQUEST);
        }
    }
}

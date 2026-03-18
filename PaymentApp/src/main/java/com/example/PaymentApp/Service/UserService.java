package com.example.PaymentApp.Service;

import com.example.PaymentApp.Entity.User;
import com.example.PaymentApp.Entity.Wallet;
import com.example.PaymentApp.Repositories.UserRepo;
import com.example.PaymentApp.Util.Jwtutil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Jwtutil jwtutil;
    @Autowired
    private WalletService walletService;

    @Autowired
    private AuthenticationManager authenticationManager;


    public void Signup(User newuser) {
        try {
            newuser.setPassword(passwordEncoder.encode(newuser.getPassword()));
            newuser.setRoles(Arrays.asList("USER"));
            walletService.InitializeWallet(newuser);
            userRepo.save(newuser);
        } catch (Exception e) {
            log.error("Unable to create user");
        }
        }





        public String Login(User newuser) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(newuser.getUserName(), newuser.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(newuser.getUserName());
             return jwtutil.Generatetoken(userDetails);

        }



}

package com.example.PaymentApp.Service;

import com.example.PaymentApp.Entity.User;
import com.example.PaymentApp.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
private UserRepo userRepo;
@Override
public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    User user = userRepo.findByuserName(userName);
    if (user == null)
        throw new UsernameNotFoundException("User not found");

    List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
    return new org.springframework.security.core.userdetails.User(
            user.getUserName(),
            user.getPassword(),
            authorities
    );
}
}

package com.example.PaymentApp.Util;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import  io.jsonwebtoken.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.jsonwebtoken.Jwts.claims;
@Component
public class Jwtutil {

    public static final String SECRET_KEY = "BmO5rPcdEYDW7BdZVxEIRc7NtXRCvNvKpsyDK3EyLDO";

    public SecretKey SigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
        //
        public String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().claims(claims).subject(subject).header().empty().add("typ", "JWT").and().issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)).signWith(SigningKey()).compact();
    }


    public String Generatetoken(UserDetails userDetails) {
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        return createToken(claims, userDetails.getUsername());
    }

    public boolean isValidToken(String token) {
        return !Expiredtoken(token);
    }

    public boolean Expiredtoken(String token) {
        return Extractexpiredtoken(token).before(new Date());
    }

    public Date Extractexpiredtoken(String token) {
        return Extractallclaims(token).getExpiration();
    }

    public Claims Extractallclaims(String token) {
        return Jwts.parser().verifyWith(SigningKey()).build().parseSignedClaims(token).getBody();
}

public String ExtractuserName(String token) {
    Claims claim =Extractallclaims(token);
    return claim.getSubject();
}

public List Extractroles(String token) {
        Claims claim = Extractallclaims(token);
    List <String> roles = claim.get("roles", List.class);   //d
    List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).toList();
    return authorities;
}






}

package com.example.PaymentApp.Filter;

import com.example.PaymentApp.Repositories.UserRepo;
import com.example.PaymentApp.Util.Jwtutil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class FilterJWT extends OncePerRequestFilter {

    @Autowired
    private Jwtutil jwtutil;

    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException {
        String authheader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authheader!= null && authheader.startsWith("Bearer ")) {
             token = authheader.substring(7);
                 username = jwtutil.ExtractuserName(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtutil.isValidToken(token)) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

        }
        try {
            filterChain.doFilter(request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        }
}

package com.workshop.Lisa.service;

import com.workshop.Lisa.Dto.AuthenticationRequest;
import com.workshop.Lisa.config.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager manager;
    private final JpaUserDetailsService jpaUDS;
    private final JwtUtils jwtU;

    public String generateToken(AuthenticationRequest req){
        System.out.println("In auth");
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUserName(), req.getUserPassword())
        );
        final UserDetails userDetails = jpaUDS.loadUserByUsername(req.getUserName());
        if(userDetails != null){
            return jwtU.generateToken(userDetails);
        }
        return "400";
    }
}

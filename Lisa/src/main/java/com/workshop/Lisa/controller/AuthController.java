package com.workshop.Lisa.controller;

import com.workshop.Lisa.Dto.AuthenticationRequest;
import com.workshop.Lisa.config.JwtUtils;
import com.workshop.Lisa.service.JpaUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager manager;
    private final JpaUserDetailsService jpaUDS;
    private final JwtUtils jwtU;

    @PostMapping("/authUser")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request){
        System.out.println("In auth");
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getUserPassword())
        );
        final UserDetails userDetails = jpaUDS.loadUserByUsername(request.getUserName());
        if(userDetails != null){
            return ResponseEntity.ok(jwtU.generateToken(userDetails));
        }
        return ResponseEntity.status(400).body("Some error has occured!");
    }

}

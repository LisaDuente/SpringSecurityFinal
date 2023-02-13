package com.workshop.Lisa.controller;


import com.workshop.Lisa.Dto.SendEmailDto;
import com.workshop.Lisa.config.JwtUtils;
import com.workshop.Lisa.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService service;
    private final JwtUtils jwtHelper;

    @RequestMapping("/sendEmail")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String sendEmail(@RequestHeader("Authorization") String token, @RequestBody SendEmailDto dto){
        token = token.substring(7);
        String username = jwtHelper.extractUsername(token);
        try{
            return this.service.sendEmail(username, dto);
        }catch(Exception e){
            System.out.println(e);
            return "An error occured!";
        }

    }
}

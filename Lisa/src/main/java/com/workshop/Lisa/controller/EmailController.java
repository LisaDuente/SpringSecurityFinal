package com.workshop.Lisa.controller;


import com.google.gson.Gson;
import com.workshop.Lisa.Dto.SendEmailDto;
import com.workshop.Lisa.config.JwtUtils;
import com.workshop.Lisa.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService service;
    private final JwtUtils jwtHelper;

    @PostMapping("/sendEmail")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String sendEmail(@RequestHeader("Authorization") String token, @RequestBody SendEmailDto dto){
        token = token.substring(7);
        String username = jwtHelper.extractUsername(token);
        Gson gson = new Gson();

        try{
            return gson.toJson(this.service.sendEmail(username, dto));
        }catch(Exception e){
            System.out.println(e);
            return gson.toJson("An error occurred!");
        }

    }
}

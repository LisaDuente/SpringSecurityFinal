package com.workshop.Lisa.controller;

import com.workshop.Lisa.Dto.UpdateUserDto;
import com.workshop.Lisa.Dto.UserDto;
import com.workshop.Lisa.config.JwtUtils;
import com.workshop.Lisa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService service;
    private final JwtUtils jwtHelper;

    @PutMapping("/updateUser")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String updateUser(@RequestBody UpdateUserDto updateUserDto, @RequestHeader("Authorization") String token){

        token = token.substring(7);
        String username = jwtHelper.extractUsername(token);
        this.service.updateUser(updateUserDto, username);

        return "Update was successful!";
    }

}

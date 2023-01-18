package com.workshop.Lisa.controller;

import com.workshop.Lisa.Dto.UserDto;
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




    @GetMapping("/needsUser")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> usersOnly(){
        return ResponseEntity.ok("fuck those admins");
    }

}

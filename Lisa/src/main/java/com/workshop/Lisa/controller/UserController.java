package com.workshop.Lisa.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("hello from User Controller");
    }

    @GetMapping("/sayGoodBye")
    public ResponseEntity<String> sayGoodBye(){
        return ResponseEntity.ok("Goodbye from User Controller");
    }

    //@PreAuthorize("hasRole('USER')")
    @GetMapping("/needsUser")
    public ResponseEntity<String> usersOnly(){
        return ResponseEntity.ok("fuck those admins");
    }

}

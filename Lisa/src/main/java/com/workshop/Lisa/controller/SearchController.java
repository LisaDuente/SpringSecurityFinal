package com.workshop.Lisa.controller;

import com.google.gson.Gson;
import com.workshop.Lisa.config.JwtUtils;
import com.workshop.Lisa.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService service;
    private final JwtUtils jwtHelper;

    @GetMapping("/searchByKeyword/{keyword}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String searchByKeyWord(@RequestHeader("Authorization") String token, @PathVariable String keyword){
        Gson gson = new Gson();
        token = token.substring(7);
        String username = jwtHelper.extractUsername(token);
        return gson.toJson(this.service.searchByUsername(keyword, username));
    }

}

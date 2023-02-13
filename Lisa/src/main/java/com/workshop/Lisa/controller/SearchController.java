package com.workshop.Lisa.controller;

import com.google.gson.Gson;
import com.workshop.Lisa.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService service;

    @GetMapping("/searchByUsername/{username}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String searchByKeyWord(@PathVariable String username){
        Gson gson = new Gson();
        return gson.toJson(this.service.findByUsername(username));

    }

}

package com.workshop.Lisa.controller;

import com.google.gson.Gson;
import com.workshop.Lisa.Dto.PreferenceDto;
import com.workshop.Lisa.config.JwtUtils;
import com.workshop.Lisa.service.PreferenceService;
import com.workshop.Lisa.service.RegionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("api/v1/preferences")
@RequiredArgsConstructor
public class PreferenceController {
    private final PreferenceService service;
    private final JwtUtils jwtHelper;

//    @PostMapping("/createPreference")
//    public String createPreference(@RequestBody PreferenceDto dto, @RequestHeader("Authorization") String token){
//        token = token.substring(7);
//        String username = jwtHelper.extractUsername(token);
//        return this.service.createPreference(dto, username);
//    }

    @GetMapping("/getAllRegions")
    public String getAllRegions(){
        Gson gson = new Gson();
        return gson.toJson(this.service.getAllRegions());
    }

    @GetMapping("/getAllHobbies")
    public String getAllHobbies(){
        Gson gson = new Gson();
        return gson.toJson(this.service.getAllHobbies());
    }
}

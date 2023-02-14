package com.workshop.Lisa.controller;

import com.google.gson.Gson;
import com.workshop.Lisa.Dto.UpdatePreferenceDto;
import com.workshop.Lisa.Dto.UpdateUserDto;
import com.workshop.Lisa.Dto.UpdateUserInformationDto;
import com.workshop.Lisa.config.JwtUtils;
import com.workshop.Lisa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

        return "{\"status\": \"Update was successful!\"}";
    }

    @GetMapping("/getUser")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String getUser(@RequestHeader("Authorization") String token){
        token = token.substring(7);
        Gson gson = new Gson();
        String username = jwtHelper.extractUsername(token);
        return gson.toJson(this.service.getUserById(this.service.findUserByUsername(username).getUserId().toString()));
    }

    @GetMapping("/getUserById/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String getUserById(@PathVariable String id){
        Gson gson = new Gson();
        return gson.toJson(this.service.getUserById(id));
    }

    @PutMapping("/updatePreferences")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String updatePreferences(@RequestBody UpdatePreferenceDto updatePreferenceDto, @RequestHeader("Authorization") String token){
        token = token.substring(7);
        String username = jwtHelper.extractUsername(token);
        this.service.updateUserPreference(updatePreferenceDto, username);

        return "Update was successful!";
    }

    @PutMapping("/updateUserInformation")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String updateUserInformation(@RequestBody UpdateUserInformationDto updateUserInformationDto, @RequestHeader("Authorization") String token){
        token = token.substring(7);
        String username = jwtHelper.extractUsername(token);
        this.service.updateUserInformation(updateUserInformationDto, username);

        return "Update was successful!";
    }

    @GetMapping("/getMatches")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String getMatches(@RequestHeader("Authorization") String token){
        Gson gson = new Gson();
        token = token.substring(7);
        String username = jwtHelper.extractUsername(token);
        return gson.toJson(this.service.getMatches(username));
    }

    @GetMapping("/getMatchPercentage/{userId}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String getMatchPercentage(@RequestHeader("Authorization") String token, @PathVariable String userId){
        Gson gson = new Gson();
        token = token.substring(7);
        String username = jwtHelper.extractUsername(token);
        return gson.toJson(this.service.getMatchPercentage(username, userId));
    }


    @PostMapping("/uploadPicture")
    public ResponseEntity<String> handleFileUpload(@RequestHeader("Authorization") String token, @RequestParam("file") MultipartFile file) {
        token = token.substring(7);
        String username = jwtHelper.extractUsername(token);
        try {
            byte[] fileBytes = file.getBytes();
                Path path = Paths.get("src/main/resources/static/images/" + file.getOriginalFilename());
                Files.write(path, fileBytes);
                this.service.uploadUserPicture(username, path.toString());
            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping("/getPicture")
    public ResponseEntity<?> getImageByName(@RequestHeader("Authorization") String token){
        token = token.substring(7);
        String username = jwtHelper.extractUsername(token);
        byte[] image = this.service.getImage(username);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
    }
}


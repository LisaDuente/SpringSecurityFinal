package com.workshop.Lisa.service;

import com.google.gson.Gson;
import com.workshop.Lisa.Dto.SearchPreferenceDto;
import com.workshop.Lisa.Dto.SearchUserDto;
import com.workshop.Lisa.Entity.Hobby;
import com.workshop.Lisa.Entity.Region;
import com.workshop.Lisa.Entity.User;
import com.workshop.Lisa.Utils.Match;
import com.workshop.Lisa.Utils.Matcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final UserService userService;
    private final Matcher matcher;


    public List<Match> searchByUsername(String keyword, String username) {
        User currentUser = userService.findUserByUsername(username);
        List<User> users = userService.getAllUsers();


        List<User> usersByName = users
                .stream()
                .filter((user1) -> {
                    String found1 = user1.getUsername().toLowerCase();
                    return found1.contains(keyword.toLowerCase());
                }).toList();

        return matcher.matchWithAllUsers(currentUser.getPreferences(), currentUser, usersByName);
    }
}

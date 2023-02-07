package com.workshop.Lisa.service;

import com.google.gson.Gson;
import com.workshop.Lisa.Dto.SearchUserDto;
import com.workshop.Lisa.Entity.Preference;
import com.workshop.Lisa.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final UserService userService;


    public List<SearchUserDto> findByKeyword(String keyword) {
        Gson gson = new Gson();
        List<User> users = userService.getAllUsers();

        List<User> usersByName = users
                .stream()
                .filter((user1) -> {
                    String found1 = user1.getUsername().toLowerCase();
                    return found1.contains(keyword.toLowerCase());
                }).toList();

        List<User> usersByPreference = users
                .stream()
                .filter((user2) -> {
                   Preference found2 = user2.getPreferences();
                   String stringPref = gson.toJson(found2).toLowerCase();
                   return stringPref.contains(keyword.toLowerCase());
                }).toList();


        HashSet<User> userSet = new HashSet<>();

        userSet.addAll(usersByName);
        userSet.addAll(usersByPreference);

        List<SearchUserDto> returnList = userSet
                .stream()
                .map(user -> new SearchUserDto(
                        user.getUsername(),
                        user.getDescription(),
                        user.getGender().toString(),
                        user.getBirthdate(),
                        user.getPreferences(),
                        user.getRegion()))
                .toList();

        return returnList;
    }
}

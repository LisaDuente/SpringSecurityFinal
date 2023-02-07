package com.workshop.Lisa.service;

import com.google.gson.Gson;
import com.workshop.Lisa.Dto.SearchPreferenceDto;
import com.workshop.Lisa.Dto.SearchUserDto;
import com.workshop.Lisa.Entity.Hobby;
import com.workshop.Lisa.Entity.Preference;
import com.workshop.Lisa.Entity.Region;
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
        //delete userID from preferences and hobby/region id from hobby/region
        List<SearchUserDto> returnList = userSet
                .stream()
                .map(user -> new SearchUserDto(
                        user.getUsername(),
                        user.getDescription(),
                        user.getGender().toString(),
                        user.getBirthdate(),
                        new SearchPreferenceDto(
                                user.getPreferences().getGender(),
                                user.getPreferences().getMaxAge(),
                                user.getPreferences().getMinAge(),
                                user.getPreferences().getRegions().stream().map(Region::getName).toArray(String[]::new),
                                user.getPreferences().getHobbies().stream().map(Hobby::getName).toArray(String[]::new)
                        ),
                        user.getRegion().getName()))
                .toList();

        return returnList;
    }
}

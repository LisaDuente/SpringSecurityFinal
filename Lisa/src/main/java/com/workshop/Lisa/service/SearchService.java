package com.workshop.Lisa.service;

import com.google.gson.Gson;
import com.workshop.Lisa.Dto.SearchPreferenceDto;
import com.workshop.Lisa.Dto.SearchUserDto;
import com.workshop.Lisa.Entity.Hobby;
import com.workshop.Lisa.Entity.Preference;
import com.workshop.Lisa.Entity.Region;
import com.workshop.Lisa.Entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.StringUtils;
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
                .map(user -> {
                        if(isInteger(keyword)){
                        int minAge = user.getPreferences().getMinAge();
                        int maxAge = user.getPreferences().getMaxAge();
                            if (Integer.parseInt(keyword) > minAge && Integer.parseInt(keyword) < maxAge){
                                return createSearchUserDto(user);
                            }
                        }
                    return createSearchUserDto(user);
                })
                .toList();

        return returnList;
    }

    private SearchUserDto createSearchUserDto(User user) {
        return new SearchUserDto(
        user.getUsername(),
        user.getDescription(),
        user.getGender().toString(),
        user.getBirthdate(),
        new SearchPreferenceDto(
                user.getPreferences().getGender().toArray(String[]::new),
                user.getPreferences().getMaxAge(),
                user.getPreferences().getMinAge(),
                user.getPreferences().getRegions().stream().map(Region::getName).toArray(String[]::new),
                user.getPreferences().getHobbies().stream().map(Hobby::getName).toArray(String[]::new)
        ),
        user.getRegion().getName());
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

}

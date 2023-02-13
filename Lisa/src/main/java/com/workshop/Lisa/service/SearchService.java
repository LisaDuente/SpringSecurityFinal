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


    public List<SearchUserDto> searchByUsername(String keyword, String username) {
        Gson gson = new Gson();
        User currentUser = userService.findUserByUsername(username);
        List<User> users = userService.getAllUsers();


        List<User> usersByName = users
                .stream()
                .filter((user1) -> {
                    String found1 = user1.getUsername().toLowerCase();
                    return found1.contains(keyword.toLowerCase());
                }).toList();

        List<Match> matches = matcher.matchWithAllUsers(currentUser.getPreferences(), currentUser, usersByName);

        List<SearchUserDto> returnList = matches
                .stream()
                .map(match -> createSearchUserDto(match))
                .toList();

        return returnList;
    }

    private SearchUserDto createSearchUserDto(Match match) {

        return new SearchUserDto(
        match.getUser().getUsername(),
        match.getUser().getDescription(),
        match.getUser().getGender().toString(),
        match.getUser().getBirthdate(),
        new SearchPreferenceDto(
                match.getUser().getPreferences().getGender().toArray(String[]::new),
                match.getUser().getPreferences().getMaxAge(),
                match.getUser().getPreferences().getMinAge(),
                match.getUser().getPreferences().getRegions().stream().map(Region::getName).toArray(String[]::new),
                match.getUser().getPreferences().getHobbies().stream().map(Hobby::getName).toArray(String[]::new)
        ),
        match.getUser().getRegion().getName(),
        String.valueOf(match.getMatchPercentage()));
    }
}

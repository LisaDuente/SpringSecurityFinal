package com.workshop.Lisa.Utils;

import com.workshop.Lisa.Dto.UserDto;
import com.workshop.Lisa.Entity.Hobby;
import com.workshop.Lisa.Entity.Preference;
import com.workshop.Lisa.Entity.Region;
import com.workshop.Lisa.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


//@NoArgsConstructor
@Service
@RequiredArgsConstructor
public class Matcher {

    public ArrayList<Match> matchWithAllUsers(Preference mePrefs, User meUser, List<User> allUsers) {

        ArrayList<Match> allMatches = new ArrayList<>();
        for (User user : allUsers) {
            if(!meUser.getUserId().equals(user.getUserId())){
                allMatches.add(matchUsers(mePrefs, user.getPreferences(), user, meUser));
            }

        }
        allMatches.sort((a, b) -> (b.getMatchPercentage() - a.getMatchPercentage()));
        return allMatches;
    }

    public Match matchUsers(Preference me, Preference you, User user, User meUser) {

        HashSet<String> shared = new HashSet<>();
        int countMatches = 0;
        //jämför users

        Set<Hobby> myHobbies = me.getHobbies();
        Set<Hobby> yourHobbies = you.getHobbies();

        if(!myHobbies.isEmpty()){
            for (Hobby hobby : myHobbies) {
                if (yourHobbies.contains(hobby)) {
                    countMatches++;
                }
                shared.add(hobby.toString());
            }
        }

        if(!myHobbies.isEmpty()){
            for (Hobby hobby : yourHobbies) {
                if (myHobbies.contains(hobby)) {
                    countMatches++;
                }
                shared.add(hobby.toString());
            }
        }

        //same for gender

        //then look at region same as in hobbies
        boolean isPreferedGender = me.getGender().equals(you.getGender());
        if (isPreferedGender) {
            countMatches++;
        }
        shared.add("genderYou");
        shared.add("genderMe");

        Set<Region> myRegions = me.getRegions();

        if (myRegions.contains(user.getRegion())) {
            countMatches++;

        }
        shared.add("regionYou");
        shared.add("regionMe");

        //compute how old user is milliseconds from user - milliseconds from now , convert milliseconds to year
        BirthDateToAgeConverter birthDateToAgeConverter = new BirthDateToAgeConverter();
        int userAge = birthDateToAgeConverter.calculateAge(Date.valueOf(user.getBirthdate()));
        if (userAge > me.getMinAge() && userAge < me.getMaxAge()) {
            countMatches++;
        }
        int myAge = birthDateToAgeConverter.calculateAge(Date.valueOf(meUser.getBirthdate()));

        if (myAge >= you.getMinAge() && myAge <= you.getMaxAge()) {
            countMatches++;
        }

        shared.add("ageYou");
        shared.add("ageMe");

        //create MatchingObject

        float percentage = 0;
        if (shared.size() != 0) {
            percentage = ((float) countMatches / (float) shared.size()) * 100;
        }

        UserDto sendBackUser = new UserDto(
                user.getUserId(),
                user.getUsername(),
                user.getDescription(),
                user.getFirstName(),
                user.getSurname(),
                user.getGender().toString(),
                user.getBirthdate(),
                user.getContactInformation(),
                user.getPreferences(),
                user.getRegion()
                );

        int intPercentage = Math.round(percentage);
        return new Match(sendBackUser, intPercentage, "success"); // cast to int works??
    }
}

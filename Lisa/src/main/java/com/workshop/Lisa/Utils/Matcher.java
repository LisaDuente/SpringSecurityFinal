package com.workshop.Lisa.Utils;

import com.workshop.Lisa.Entity.Hobby;
import com.workshop.Lisa.Entity.Preference;
import com.workshop.Lisa.Entity.Region;
import com.workshop.Lisa.Entity.User;
import com.workshop.Lisa.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
//@RequiredArgsConstructor
public class Matcher {
    //some problems here
    private UserService userService;

    public ArrayList<Match> matchWithAllUsers(Preference mePrefs, User meUser, List<User> allUsers) {

        ArrayList<Match> allMatches = new ArrayList<>();
        for (User user : allUsers) {
            allMatches.add(matchUsers(mePrefs, user.getPreferences(), user, meUser));
        }
        return allMatches;
    }

    public Match matchUsers (Preference me, Preference you){
        //create userSharedPreferenceSet
        HashSet<String> shared = new HashSet<>();
        int countMatches = 0;
        //jämör users
        Set<Hobby> myHobbies = me.getHobbies();
        Set<Hobby> yourHobbies = you.getHobbies();

        for(Hobby hobby : myHobbies){
            if(yourHobbies.contains(hobby)){
                countMatches++;
            }
            shared.add(hobby.toString());
        }

        for(Hobby hobby : yourHobbies){
            if(myHobbies.contains(hobby)){
                countMatches++;
            }
            shared.add(hobby.toString());
        }

        //same for gender

        //then look at region same as in hobbies
        boolean isPreferedGender = me.getGender().equals(you.getGender());
        if (isPreferedGender) {
            countMatches++;
        }
        shared.add("genderYou");
        shared.add("genderMe");

        Set<Region> myRegions = me.getRegion();
        Set<Region> yourRegions = you.getRegion();

        boolean isInRegion = false;
        for(Region region : myRegions){
            if(yourRegions.contains(region)){
                isInRegion = true;
                countMatches++;
                break;
            }
        }
        //User user = userService.findById(you.getUserId());
        //compute how old user is milliseconds from user - milliseconds from now , convert milliseconds to year
        //int userAge = user.getBirthDate().
        //boolean isInAgeRange = me.getMinAge() < user.

        //compute how old user is milliseconds from user - milliseconds from now , convert milliseconds to year
        BirthDateToAgeConverter birthDateToAgeConverter = new BirthDateToAgeConverter();
        int userAge = birthDateToAgeConverter.calculateAge(user.getBirthDate());
        if (userAge > Integer.parseInt(me.getMinAge()) && userAge < Integer.parseInt(me.getMaxAge())) {
            countMatches++;
        }
        int myAge = birthDateToAgeConverter.calculateAge(meUser.getBirthDate());
        System.out.println("myAge: " + myAge);
        System.out.println("you.getMinAge(): " + you.getMinAge());
        System.out.println("you.getMaxAge(): " + you.getMaxAge());
        if (myAge >= Integer.parseInt(you.getMinAge()) && myAge <= Integer.parseInt(you.getMaxAge())) {
            System.out.println("countMatches: " + countMatches);
            countMatches++;
            System.out.println("inrange");
        }

        shared.add("ageYou");
        shared.add("ageMe");

        //create MatchingObject
        System.out.println("counter: " + countMatches);
        System.out.println("sharedSet: " + shared.size());
        double percentage = (countMatches / shared.size()) * 100;

        System.out.println("percentage: " + percentage + " %");

        System.out.println("Match object: " + new Match(user, percentage));

        return new Match(user, percentage);
    }
}

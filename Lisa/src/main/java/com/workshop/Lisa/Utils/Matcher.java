package com.workshop.Lisa.Utils;

import com.workshop.Lisa.Entity.Hobby;
import com.workshop.Lisa.Entity.Preference;
import com.workshop.Lisa.Entity.Region;
import com.workshop.Lisa.Entity.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
//@RequiredArgsConstructor
@AllArgsConstructor
@Service
public class Matcher {


    public ArrayList<Match> matchWithAllUsers(Preference mePrefs, User meUser, List<User> allUsers) {

        ArrayList<Match> allMatches = new ArrayList<>();
        for (User user : allUsers) {
            allMatches.add(matchUsers(mePrefs, user.getPreferences(), user, meUser));
        }
        //ArrayList<Match> sorted = (ArrayList<Match>) allMatches.stream()
         //       .filter((match -> match.getMatchPercentage() > 0))
          //      .collect(Collectors.toList());
        allMatches.sort((a,b) -> (int) (b.getMatchPercentage() - (int) a.getMatchPercentage()));
        return allMatches;
    }

    public Match matchUsers(Preference me, Preference you, User user, User meUser) {
        //create userSharedPreferenceSet
        HashSet<String> shared = new HashSet<>();
        int countMatches = 0;
        //jämför users
        Set<Hobby> myHobbies = me.getHobbies();
        Set<Hobby> yourHobbies = you.getHobbies();

        for (Hobby hobby : myHobbies) {
            if (yourHobbies.contains(hobby)) {
                countMatches++;
            }
            shared.add(hobby.toString());
        }

        for (Hobby hobby : yourHobbies) {
            if (myHobbies.contains(hobby)) {
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
        for (Region region : myRegions) {
            if (yourRegions.contains(region)) {
                isInRegion = true;
                countMatches++;
                break;
            }
        }
        shared.add("regionYou");
        shared.add("regionMe");

        //compute how old user is milliseconds from user - milliseconds from now , convert milliseconds to year
        BirthDateToAgeConverter birthDateToAgeConverter = new BirthDateToAgeConverter();
        int userAge = birthDateToAgeConverter.calculateAge(user.getBirthDate());
        if (userAge > Integer.parseInt(me.getMinAge()) && userAge < Integer.parseInt(me.getMaxAge())) {
            countMatches++;
        }
        int myAge = birthDateToAgeConverter.calculateAge(meUser.getBirthDate());
        if (myAge >= Integer.parseInt(you.getMinAge()) && myAge <= Integer.parseInt(you.getMaxAge())) {
            System.out.println("countMatches: " + countMatches);
            countMatches++;
        }

        shared.add("ageYou");
        shared.add("ageMe");

        //create MatchingObject

        float percentage = 0;
        if(shared.size() != 0) {
            percentage = ((float) countMatches / (float) shared.size()) * 100;
        }

        int intPercentage = Math.round(percentage);
        return new Match(user, intPercentage); // cast to int works??
    }
}

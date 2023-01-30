package com.workshop.Lisa.Utils;

import com.workshop.Lisa.Entity.Hobby;
import com.workshop.Lisa.Entity.Preference;
import com.workshop.Lisa.Entity.Region;
import com.workshop.Lisa.Entity.User;
import com.workshop.Lisa.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
//@RequiredArgsConstructor
public class Matcher {
    //some problems here
    //private final UserService userService;

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
        //check if age is in my agespan
        //check if my age is in you agespan
        //if both is false add two in set, if both is true set one in set and increase matchCounter
        //same for gender

        //then look at region same as in hobbies
        boolean isPreferedGender = me.getGender().equals(you.getGender());

        Set<Region> myRegions = me.getRegion();
        Set<Region> yourRegions = you.getRegion();

        boolean isInRegion = false;
        for(Region region : myRegions){
            if(yourRegions.contains(region)){
                isInRegion = true;
                break;
            }
        }
        //User user = userService.findById(you.getUserId());
        //compute how old user is milliseconds from user - milliseconds from now , convert milliseconds to year
        //int userAge = user.getBirthDate().
        //boolean isInAgeRange = me.getMinAge() < user.

        //match age
        //getUser from prefId
        //compute userAge from birthDate
        //get minAge and MaxAge from preference, match if userAge > min && userAge < max
        //if so, increase matchCounter

        //create MatchingObject

        //--------------------
        /* TOROS: how to convert date object milliseconds to current age in java
        Here's a simple example of how to convert a date object in milliseconds to the current age in Java:

java
Copy code
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class AgeCalculator {
  public static void main(String[] args) {
    long millis = System.currentTimeMillis();
    Date birthDate = new Date(millis);
    LocalDate now = LocalDate.now();
    LocalDate birthLocalDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    Period age = Period.between(birthLocalDate, now);
    System.out.println("Age: " + age.getYears() + " years, " + age.getMonths() + " months, " + age.getDays() + " days");
  }
}
This code converts the current time in milliseconds to a Date object, and then converts that Date object to a LocalDate object. Then, it uses the Period.between() method to calculate the difference between the birth date and the current date, resulting in the age in years, months, and days.
         */
        //--------------------

        return new Match();
    }
}

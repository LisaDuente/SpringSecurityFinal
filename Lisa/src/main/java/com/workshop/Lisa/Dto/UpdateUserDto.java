package com.workshop.Lisa.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {

    private String userEmail;
    private String userFirstname;
    private String userLastName;
    private String gender;
    private String birthDate;
    private String userRegion;
    private String description;
    private String facebook;
    private String instagram;
    private String discord;
    private String snapchat;
    private String userPhoneNumber;
    private String minAge;
    private String maxAge;
    private String[] hobbies;
    private String[] regions;
    private String preferedGender;

}

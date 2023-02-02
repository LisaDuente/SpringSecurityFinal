package com.workshop.Lisa.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserInformationDto {
    private String userEmail;
    private String userFirstname;
    private String userLastName;
    private String gender;
    private String birthDate;
    private String facebook;
    private String instagram;
    private String discord;
    private String snapchat;
    private String userPhoneNumber;
    private String userRegion;
}

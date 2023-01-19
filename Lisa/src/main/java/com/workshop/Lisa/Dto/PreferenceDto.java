package com.workshop.Lisa.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceDto {
    private String userID;

    private String gender;

    private String maxAge;

    private String minAge;

    private String[] regions;

    private String[] hobbies;

}

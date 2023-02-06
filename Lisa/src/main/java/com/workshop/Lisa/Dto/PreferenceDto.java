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

    private int maxAge;

    private int minAge;

    private String[] regions;

    private String[] hobbies;

}

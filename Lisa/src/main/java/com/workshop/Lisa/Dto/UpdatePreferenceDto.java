package com.workshop.Lisa.Dto;

import lombok.*;

@Data
public class UpdatePreferenceDto {
    private String minAge;
    private String maxAge;
    private String[] hobbies;
    private String[] regions;
    private String preferedGender;
}

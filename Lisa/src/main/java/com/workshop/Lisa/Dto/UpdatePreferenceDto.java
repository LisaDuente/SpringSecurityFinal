package com.workshop.Lisa.Dto;

import lombok.*;

@Data
public class UpdatePreferenceDto {
    private int minAge;
    private int maxAge;
    private String[] hobbies;
    private String[] regions;
    private String preferedGender;
}

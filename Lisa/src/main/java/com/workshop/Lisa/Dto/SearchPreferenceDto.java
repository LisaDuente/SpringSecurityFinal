package com.workshop.Lisa.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchPreferenceDto {

    private String gender;

    private int maxAge;

    private int minAge;

    private String[] regions;

    private String[] hobbies;
}

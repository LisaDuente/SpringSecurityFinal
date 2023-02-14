package com.workshop.Lisa.Dto;

import com.workshop.Lisa.Entity.ContactInformation;
import com.workshop.Lisa.Entity.Preference;
import com.workshop.Lisa.Entity.Region;
import com.workshop.Lisa.Utils.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String username;
    private String description;
    private String firstName;
    private String surname;
    private String gender;
    private String birthdate;
    private ContactInformation contactInformation;
    private Preference preferences;
    private Region region;

    private String photo;
}

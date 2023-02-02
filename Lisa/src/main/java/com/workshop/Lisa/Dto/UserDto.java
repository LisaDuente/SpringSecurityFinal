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
    private String userName;
    private String description;
    private String userFirstname;
    private String userLastName;
    private String userGender;
    private Date birthDate;
    private Preference preferences;
    private Region userRegion;
}

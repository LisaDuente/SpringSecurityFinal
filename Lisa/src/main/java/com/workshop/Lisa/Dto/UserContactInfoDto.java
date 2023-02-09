package com.workshop.Lisa.Dto;

import com.workshop.Lisa.Entity.ContactInformation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserContactInfoDto {

        private long userId;
        private String username;
        private String firstName;
        private String surname;
        private ContactInformation contactInformation;
        private String status;


}

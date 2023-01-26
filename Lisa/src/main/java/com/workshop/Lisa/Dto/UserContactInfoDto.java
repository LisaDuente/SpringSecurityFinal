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

        private long userID;
        private String userName;
        private String userEmail;
        private ContactInformation contactInformation;
        private String status;

}

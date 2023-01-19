package com.workshop.Lisa.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String userName;
    private String userPassword;
    private String userEmail;
    private String userFirstname;
    private String userLastName;
    private String roles;
    private String gender;
    private String birthDate;
    //and more!
}

package com.workshop.Lisa.Entity;

import com.workshop.Lisa.Utils.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String userName;
    private String userEmail;
    private String userFirstname;
    private String userLastName;
    private String userPassword;
    private String roles;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private Date birthDate;
    //preferences here

}


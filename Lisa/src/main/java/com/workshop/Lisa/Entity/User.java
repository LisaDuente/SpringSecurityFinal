package com.workshop.Lisa.Entity;

import com.workshop.Lisa.Utils.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @Column(unique = true)
    private String userName;
    @Column(unique = true)
    private String userEmail;
    private String userFirstname;
    private String userLastname;
    private String userPassword;
    private Date age;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String roles;
    //preferences here

    public User(String name, String email,String firstname, String lastname, String password, String roles, Date age, GenderEnum gender) {
        this.userName = name;
        this.userEmail = email;
        this.userFirstname = firstname;
        this.userLastname = lastname;
        this.userPassword = password;
        this.roles = roles;
        this.age = age;
        this.gender = gender;
    }
}
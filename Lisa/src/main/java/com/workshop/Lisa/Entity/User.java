package com.workshop.Lisa.Entity;

import com.workshop.Lisa.Utils.GenderEnum;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @Column(unique = true)
    private String username;
    private String description;
    private String firstName;
    private String surname;
    private String password;
    private String roles;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String birthdate;
    @OneToOne
    private ContactInformation contactInformation;
    @OneToOne
    private Preference preferences;
    @ManyToOne
    private Region region;

    //preferences here

}


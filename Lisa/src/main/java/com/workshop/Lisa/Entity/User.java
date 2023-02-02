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
    private String userName;
    private String description;
    private String userFirstname;
    private String userLastName;
    private String userPassword;
    private String roles;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private Date birthDate;
    @OneToOne
    private ContactInformation contactInformation;
    @OneToOne
    private Preference preferences;
    @ManyToOne
    private Region userRegion;

    //preferences here

}


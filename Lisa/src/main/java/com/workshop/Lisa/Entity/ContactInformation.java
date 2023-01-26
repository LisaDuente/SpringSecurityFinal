package com.workshop.Lisa.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userID;
    @Column(unique = true)
    private String userEmail;
    private String discord;
    private String snapchat;
    private String instagram;
    private String facebook;
    private String userPhoneNumber;
}

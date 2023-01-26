package com.workshop.Lisa.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userID;
    private String discord;
    private String snapchat;
    private String instagram;
}

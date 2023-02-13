package com.workshop.Lisa.Entity;

import com.workshop.Lisa.Utils.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Preference {

    @Id
    private long userId;
    private int minAge;
    private int maxAge;
    @ElementCollection
    private Set<String> gender;
    //    private String gender;
    @ManyToMany
    @JoinTable(
            name = "regionUser",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private Set<Region> regions;
    @ManyToMany
    @JoinTable(
            name = "hobbyUser",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private Set<Hobby> hobbies;
}
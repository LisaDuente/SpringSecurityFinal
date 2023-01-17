package com.workshop.Lisa.Entity;

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
    private String userId;
    private String minAge;
    private String maxAge;
    private String gender;
    @ManyToMany
    @JoinTable(
            name = "regionUser",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "regionId")
    )
    private Set<Region> region;
    @ManyToMany
    @JoinTable(
            name = "hobbyUser",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "hobbyId")
    )
    private Set<Hobby> hobbies;

}

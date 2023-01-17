package com.workshop.Lisa.Entity;

import com.workshop.Lisa.Utils.HobbyEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Hobby {
    @Id
    private String hobbyId;
    @Enumerated(EnumType.STRING)
    private HobbyEnum hobby;
}

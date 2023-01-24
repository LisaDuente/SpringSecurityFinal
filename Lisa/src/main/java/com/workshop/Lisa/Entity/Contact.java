package com.workshop.Lisa.Entity;

import com.workshop.Lisa.Utils.ContactEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@Setter
@Getter
public class Contact {

    @Id
    private Long friendId;
    private Long userOne;
    private Long userTwo;
    @Enumerated(EnumType.STRING)
    private ContactEnum status;
}

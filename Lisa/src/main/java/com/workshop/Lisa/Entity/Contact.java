package com.workshop.Lisa.Entity;

import com.workshop.Lisa.Utils.ContactEnum;
import com.workshop.Lisa.Utils.ContactKeys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@IdClass(ContactKeys.class)
@AllArgsConstructor
@NoArgsConstructor
public class Contact {

    @Id
    private Long userOne;
    @Id
    private Long userTwo;
    @Enumerated(EnumType.STRING)
    private ContactEnum status;
}

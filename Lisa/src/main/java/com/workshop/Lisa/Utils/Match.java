package com.workshop.Lisa.Utils;

import com.workshop.Lisa.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Match {
    private User user;
    private double matchPercentage;

}

package com.workshop.Lisa.Utils;

import com.workshop.Lisa.Dto.UserDto;
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
    private UserDto user;
    private int matchPercentage;
    private String message;

}

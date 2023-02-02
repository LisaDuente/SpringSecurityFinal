package com.workshop.Lisa.Utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

public class BirthDateToAgeConverter {

    public int calculateAge(Date birthDate) {
        LocalDate bd = birthDate.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(bd, currentDate);
        return period.getYears();
    }
}

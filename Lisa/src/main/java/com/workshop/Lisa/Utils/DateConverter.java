package com.workshop.Lisa.Utils;

import com.workshop.Lisa.Dto.UserDto;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@NoArgsConstructor
public class DateConverter {


    public Date getDate(String birthDate) {
        java.util.Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date age = new Date(Long.parseLong(String.valueOf(date.getTime()))); //Set the age in frontend på birthdate?
        System.out.println(age);
        return age;
    }
}

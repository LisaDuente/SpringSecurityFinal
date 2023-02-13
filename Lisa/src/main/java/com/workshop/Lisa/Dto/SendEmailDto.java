package com.workshop.Lisa.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendEmailDto {
    private String userID;
    private String content;
}

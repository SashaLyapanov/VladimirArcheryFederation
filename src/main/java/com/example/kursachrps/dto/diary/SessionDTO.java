package com.example.kursachrps.dto.diary;

import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class SessionDTO {
    private int id;
    private Date date;
    private Date dateTime;
    private Time duration;
    private SessionTypeDTO sessionType;
}

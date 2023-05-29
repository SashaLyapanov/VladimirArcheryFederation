package com.example.kursachrps.dto.diary;

import lombok.Data;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
public class GetExercisesDTO {
    private int id;
    private Date date;
    private Date dateTime;
    private Time duration;
    private SessionTypeDTO sessionType;
    private List<SessionExercisesDTO> sessionExercises;
}
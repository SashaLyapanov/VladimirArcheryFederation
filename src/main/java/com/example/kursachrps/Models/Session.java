package com.example.kursachrps.Models;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Session {
    private int id;
    private String name;
    //dateTime - нужно подобрать тип данных для работы с датой и временем.
    private Date dateTime;
    //duration - нужно подобрать тип данных для работы с датой и временем.
    private Time duration;
    private Diary diary;
    private int points;
    private List<Exercise> exercises;
}

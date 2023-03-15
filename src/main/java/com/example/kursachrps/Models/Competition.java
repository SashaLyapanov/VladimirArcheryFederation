package com.example.kursachrps.Models;

import java.util.Date;
import java.util.List;

public class Competition {
    private int id;
    private String name;
    private Date date;
    private String judge;
    private List<Application> applications;
    private List<Sportsman> sportsmanList;
    private Results results;
}

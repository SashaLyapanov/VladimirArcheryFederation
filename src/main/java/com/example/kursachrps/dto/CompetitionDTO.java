package com.example.kursachrps.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CompetitionDTO {

    private String title;
    private String place;
    private List<String> category;
    private Date date;

}

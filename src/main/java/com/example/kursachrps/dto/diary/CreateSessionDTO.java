package com.example.kursachrps.dto.diary;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateSessionDTO {
    private Date date;
    private Time duration;
    private SessionTypeDTOId sessionType;
    private SportsmanDiaryDTO sportsman;
}
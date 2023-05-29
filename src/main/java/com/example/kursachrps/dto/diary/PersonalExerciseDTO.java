package com.example.kursachrps.dto.diary;

import lombok.Data;

@Data
public class PersonalExerciseDTO {
    private int id;
    private SportsmanDiaryDTO sportsman;
    private String name;
    private String description;
    private String photo;
}

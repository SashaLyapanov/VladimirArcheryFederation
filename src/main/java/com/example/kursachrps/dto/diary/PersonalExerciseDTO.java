package com.example.kursachrps.dto.diary;

import lombok.Data;

@Data
public class PersonalExerciseDTO {
    private String id;
    private SportsmanDiaryDTO sportsman;
    private String name;
    private String description;
    private String photo;
}

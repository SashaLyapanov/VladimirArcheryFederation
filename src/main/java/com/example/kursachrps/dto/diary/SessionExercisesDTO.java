package com.example.kursachrps.dto.diary;

import lombok.Data;

@Data
public class SessionExercisesDTO {
    private int id;
    private Integer orderExercise;
    private Integer countApproach;
    private Integer countRepeat;
    private String description;
    private ExerciseDTO exercise;
    private PersonalExerciseDTO personalExercise;
}

package com.example.kursachrps.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "session_sessionExercises")
public class SessionExercises {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Упражнение должно иметь название")
    @Column(name = "exercise_name")
    private String exeName;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "sessionExercisesList")
    private List<Session> sessionList;

    @ManyToMany(mappedBy = "sessionExercisesList")
    private List<Exercise> exerciseList;

    @ManyToMany(mappedBy = "sessionExercisesList")
    private List<PersonalExercise> personalExerciseList;
}

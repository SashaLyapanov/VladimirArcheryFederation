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
    @Column(name = "session_exercise_id")
    private String exeName;

//    @Column(name = "description")
//    private String description;

    @ManyToMany(mappedBy = "sessionExercisesList", fetch = FetchType.LAZY)
    private List<Session> sessionList;

    @ManyToMany(mappedBy = "sessionExercisesList", fetch = FetchType.LAZY)
    private List<Exercise> exerciseList;

    @ManyToMany(mappedBy = "sessionExercisesList", fetch = FetchType.LAZY)
    private List<PersonalExercise> personalExerciseList;
}

package com.example.kursachrps.Models;

import jakarta.persistence.*;
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

//    @ManyToMany(mappedBy = "sessionExercisesList", fetch = FetchType.LAZY)
//    private List<Session> sessionList;

    @OneToOne(mappedBy = "sessionExercises")
    private Session sessionList;

    @ManyToMany(mappedBy = "sessionExercisesList", fetch = FetchType.LAZY)
    private List<Exercise> exerciseList;

    @ManyToMany(mappedBy = "sessionExercisesList", fetch = FetchType.LAZY)
    private List<PersonalExercise> personalExerciseList;
}

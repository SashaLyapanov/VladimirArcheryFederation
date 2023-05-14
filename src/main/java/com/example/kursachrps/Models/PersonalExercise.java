package com.example.kursachrps.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "personal_exercises")
public class PersonalExercise {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "exercise_name")
    @NotEmpty(message = "Упражнение обязательно должно иметь название")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "photo")
    private String photo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sportsman_id", referencedColumnName = "id")
    private Sportsman sportsman;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "personal_exercises_session_exercises",
            joinColumns = @JoinColumn(name = "personal_exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "session_exercise_id"))
    private List<SessionExercises> sessionExercisesList;
}

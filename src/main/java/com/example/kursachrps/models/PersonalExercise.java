package com.example.kursachrps.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "personal_exercises")
public class PersonalExercise extends GenericEntity {

//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;

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

    @OneToMany(mappedBy = "personalExercise")
    private List<SessionExercises> sessionExercisesList;
}
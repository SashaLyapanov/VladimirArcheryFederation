package com.example.kursachrps.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "session_exercises")
public class SessionExercises {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "order_exercise")
    private int orderExercise;

    @Column(name = "count_approach")
    private int countApproach;

    @Column(name = "count_repeat")
    private int countRepeat;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", referencedColumnName = "id")
    private Session sessionExercises;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercises_id",referencedColumnName = "id")
    private Exercise exercise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_exercises_id",referencedColumnName = "id")
    private PersonalExercise personalExercise;

}

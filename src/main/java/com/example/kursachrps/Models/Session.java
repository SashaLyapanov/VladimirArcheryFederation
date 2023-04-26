package com.example.kursachrps.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //dateTime - нужно подобрать тип данных для работы с датой и временем.
    @Column(name = "date_and_time")
    private Date dateTime;
    //duration - нужно подобрать тип данных для работы с датой и временем.
    @Column(name = "duration")
    private Time duration;

    @ManyToOne
    @JoinColumn(name = "session_type_id", referencedColumnName = "id")
    private SessionType sessionType;


    @ManyToOne
    @JoinColumn(name = "sportsman_id", referencedColumnName = "id")
    private Sportsman sportsman;

    @ManyToMany
    @JoinTable(
            name = "session_sessionExercises",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "session_exercise_id"))
    private List<SessionExercises> sessionExercisesList;
}

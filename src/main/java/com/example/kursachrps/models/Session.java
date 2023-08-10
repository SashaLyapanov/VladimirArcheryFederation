package com.example.kursachrps.models;

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
    @Column(name = "date_time")
    private Date dateTime;

    //date - чисто дата тренировки
    @Column(name = "date")
    private Date date;

    //duration - нужно подобрать тип данных для работы с датой и временем.
    @Column(name = "duration")
    private Time duration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_type_id", referencedColumnName = "id")
    private SessionType sessionType;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sportsman_id", referencedColumnName = "id")
    private Sportsman sportsman;

    @OneToMany(mappedBy = "sessionExercises")
    private List<SessionExercises> sessionExercisesList;

}


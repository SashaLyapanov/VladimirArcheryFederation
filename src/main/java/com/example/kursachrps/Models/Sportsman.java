package com.example.kursachrps.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "sportsmen")
public class Sportsman extends User {
//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;

//    @NotEmpty
//    @OneToOne()
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id", referencedColumnName = "id")
    private Coach personal_coach;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sports_title_id", referencedColumnName = "id")
    private SportsTitle sportsTytle;

    @OneToMany(mappedBy = "sportsman")
    private List<Achievement> achievementList;

    @OneToMany(mappedBy = "sportsman")
    private List<Session> sessionList;

    @OneToMany(mappedBy = "sportsman")
    private List<Application> applicationList;

    @OneToMany(mappedBy = "sportsman")
    private List<PersonalExercise> personalExerciseList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Sportsman_Competition",
            joinColumns = @JoinColumn(name = "sportsman_id"),
            inverseJoinColumns = @JoinColumn(name = "competition_id"))
    private List<Competition> competitionList;
}

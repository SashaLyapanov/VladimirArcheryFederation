package com.example.kursachrps.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "sportsmans")
public class Sportsman extends User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @NotEmpty
//    @OneToOne()
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "userName", column = @Column(name = "user_name")),
            @AttributeOverride(name = "password", column = @Column(name = "user_password")),
            @AttributeOverride(name = "role", column = @Column(name = "user_role")),
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name")),
            @AttributeOverride(name = "surname", column = @Column(name = "user_surname")),
            @AttributeOverride(name = "patronymic", column = @Column(name = "patronymic")),
            @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
    })
    private User user;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "coach_id", referencedColumnName = "id")
    private Coach coach;

    @ManyToOne
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

    @ManyToMany
    @JoinTable(
            name = "Sportsman_Competition",
            joinColumns = @JoinColumn(name = "sportsman_id"),
            inverseJoinColumns = @JoinColumn(name = "competition_id"))
    private List<Competition> competitionList;


}

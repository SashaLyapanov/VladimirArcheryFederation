package com.example.kursachrps.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "sportsmen")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sportsman extends User {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id", referencedColumnName = "id")
    private Coach personal_coach;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sports_title_id", referencedColumnName = "id")
    private SportsTitle sportsTitle;

    @JsonIgnore
    @OneToMany(mappedBy = "sportsman")
    private List<Achievement> achievementList;

    @JsonIgnore
    @OneToMany(mappedBy = "sportsman")
    private List<Session> sessionList;

    @JsonIgnore
    @OneToMany(mappedBy = "sportsman")
    private List<Application> applicationList;

    @JsonIgnore
    @OneToMany(mappedBy = "sportsman")
    private List<PersonalExercise> personalExerciseList;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Sportsman_Competition",
            joinColumns = @JoinColumn(name = "sportsman_id"),
            inverseJoinColumns = @JoinColumn(name = "competition_id"))
    private List<Competition> competitionList;


    public Sportsman() {
    }

    public Sportsman(String email, String password, Role role, Status status, String firstName, String surname, String patronymic, Date birthDate,
                     Team team, Coach personal_coach, SportsTitle sportsTitle) {
        super(email, password, role, status, firstName, surname,patronymic,birthDate);
        this.team = team;
        this.personal_coach = personal_coach;
        this.sportsTitle = sportsTitle;
    }

}

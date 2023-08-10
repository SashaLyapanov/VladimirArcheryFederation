package com.example.kursachrps.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "sportsmen")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sportsman extends User {

    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region;

    @ManyToOne
    @JoinColumn(name = "sex_id", referencedColumnName = "id")
    private Sex sex;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "coach_id", referencedColumnName = "id")
    private Coach personalCoach;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
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
                     Team team, Coach personalCoach, SportsTitle sportsTitle) {
        super(email, password, role, status, firstName, surname,patronymic,birthDate);
        this.team = team;
        this.personalCoach = personalCoach;
        this.sportsTitle = sportsTitle;
    }

}

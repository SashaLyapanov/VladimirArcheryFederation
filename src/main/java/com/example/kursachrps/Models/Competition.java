package com.example.kursachrps.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
@Entity
@Table(name = "competition")
public class Competition {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "competition_name")
    @NotEmpty(message = "Название соревнованй не может быть пустым")
    private String name;

    @Column(name = "date")
    @NotEmpty(message = "Соревнование должно иметь дату проведения")
    private Date date;

    //Решить с судьями!!! Т.к. у нас в системе 1 судья, нам надо просто каждым соревам присваивать строкове имя судьи,
    //который по факту ведет соревнования.
    @Column(name = "judge")
    private String judge;

    @OneToMany(mappedBy = "competition")
    private List<Application> applications;

    @ManyToMany(mappedBy = "competitionList")
    private List<Sportsman> sportsmanList;

    @OneToMany(mappedBy = "competition")
    private List<Achievement> achievementList;

    @ManyToMany
    @JoinTable(
            name = "Competition_BowType",
            joinColumns = @JoinColumn(name = "competiiton_id"),
            inverseJoinColumns = @JoinColumn(name = "bow_type_id"))
    private List<BowType> bowTypeList;
}

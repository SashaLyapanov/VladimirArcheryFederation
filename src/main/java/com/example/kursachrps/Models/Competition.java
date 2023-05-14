package com.example.kursachrps.Models;

import com.example.kursachrps.dto.AdditionalDTO.BowTypeDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.util.Date;
import java.util.List;
@Data
@Entity
@Table(name = "competitions")
public class Competition {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "competition_name")
    @NotEmpty(message = "Название соревнованй не может быть пустым")
    private String name;


    @Column(name = "place")
    @NotEmpty(message = "Место должно быть!")
    private String place;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private CompetitionType type;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    @JoinTable(
            name = "competitions_categories",
            joinColumns = @JoinColumn(name = "competition_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;


    @Column(name = "competition_date")
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
            name = "competition_bow_type",
            joinColumns = @JoinColumn(name = "competition_id"),
            inverseJoinColumns = @JoinColumn(name = "bow_type_id"))
    private List<BowType> bowTypeList;
}

package com.example.kursachrps.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "bow_types")
public class BowType {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "bow_type_name")
    private String bowTypeName;

    @ManyToMany(mappedBy = "bowTypeList")
    private List<Coach> coachList;

    @OneToMany(mappedBy = "bowType")
    private List<Application> applicationList;

    @ManyToMany(mappedBy = "bowTypeList")
    private List<Competition> competitionList;
}

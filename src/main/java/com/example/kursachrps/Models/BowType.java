package com.example.kursachrps.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "bow_types")
public class BowType {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "bow_type_name")
    @NotEmpty
    private String bowTypeName;

    @OneToMany(mappedBy = "bowType")
    private List<Application> applicationList;

    @ManyToMany(mappedBy = "bowTypeList")
    private List<Competition> competitionList;

    @ManyToMany(mappedBy = "bowTypeList")
    private List<Coach> coachList;
}

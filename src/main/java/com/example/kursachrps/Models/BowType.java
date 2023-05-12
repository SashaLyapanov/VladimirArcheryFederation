package com.example.kursachrps.Models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

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
    private String bowTypeName;

    @ManyToMany(mappedBy = "bowTypeList")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
    private List<Coach> coachList;

    @OneToMany(mappedBy = "bowType")
    private List<Application> applicationList;

    @ManyToMany(mappedBy = "bowTypeList")
    private List<Competition> competitionList;
}

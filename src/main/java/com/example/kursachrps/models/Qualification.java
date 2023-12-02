package com.example.kursachrps.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "qualifications")
public class Qualification extends GenericEntity{
//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;

    @Column(name = "qualifications_name")
    private String qualificationName;

    @OneToMany(mappedBy = "qualification")
    private List<Coach> coachList;

}

package com.example.kursachrps.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "competition_type")
@Data
public class CompetitionType extends GenericEntity{

//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "type")
    private Set<Competition> competitions;
}

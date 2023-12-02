package com.example.kursachrps.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "regions")
@Data
public class Region extends GenericEntity{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private List<Sportsman> sportsmen;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private List<Coach>  coaches;
}

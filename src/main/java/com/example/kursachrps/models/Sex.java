package com.example.kursachrps.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sex")
@Data
public class Sex extends GenericEntity{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private int id;

    @Column(name = "name")
    private String name;
}

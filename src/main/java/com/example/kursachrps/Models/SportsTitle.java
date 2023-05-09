package com.example.kursachrps.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "sports_titles")
public class SportsTitle {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sports_title_name")
    private String name;

    @OneToMany(mappedBy = "sportsTitle", cascade = CascadeType.ALL)
    private List<Sportsman> sportsmanList;
}

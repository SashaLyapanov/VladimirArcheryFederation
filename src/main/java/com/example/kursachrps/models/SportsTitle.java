package com.example.kursachrps.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "sports_titles")
public class SportsTitle extends GenericEntity{
    @Column(name = "sports_title_name")
    private String name;

    @OneToMany(mappedBy = "sportsTitle", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Sportsman> sportsmanList;
}

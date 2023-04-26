package com.example.kursachrps.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "qualification")
public class Qualification {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "qualification_name")
    @NotEmpty
    private String qualificationName;

    @OneToMany(mappedBy = "qualification")
    private List<Coach> coachList;

}

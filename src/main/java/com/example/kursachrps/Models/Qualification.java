package com.example.kursachrps.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "qualifications")
public class Qualification {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "qualifications_name")
    @NotEmpty
    private String qualificationName;

    @OneToMany(mappedBy = "qualification")
    private List<Coach> coachList;

}

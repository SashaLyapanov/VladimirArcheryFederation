package com.example.kursachrps.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "competition_type")
@Data
public class CompetitionType extends GenericEntity{
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Competition> competitions;
}

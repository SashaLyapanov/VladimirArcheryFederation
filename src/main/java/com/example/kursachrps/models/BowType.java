package com.example.kursachrps.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "bow_types")
public class BowType extends GenericEntity {
    @Column(name = "bow_type_name")
    private String bowTypeName;

    @OneToMany(mappedBy = "bowType")
    private List<Application> applicationList;

    @ManyToMany(mappedBy = "bowTypeList")
    private List<Competition> competitionList;
}

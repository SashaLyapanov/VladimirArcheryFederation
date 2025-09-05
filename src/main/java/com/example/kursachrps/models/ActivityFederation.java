package com.example.kursachrps.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class ActivityFederation extends GenericEntity{

    @Column(name = "file_names_3d")
    private String fileNamesThreeD;

    @Column(name = "file_names_classic")
    private String fileNamesClassic;

    @Column(name = "file_names_Biathlon")
    private String fileNamesBiathlon;

    @Column(name = "file_names_General")
    private String fileNamesGeneral;
}

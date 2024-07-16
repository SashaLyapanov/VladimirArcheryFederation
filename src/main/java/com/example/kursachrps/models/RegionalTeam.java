package com.example.kursachrps.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity(name = "RegionalTeam")
@Data
public class RegionalTeam extends GenericEntity{
    @Column(name = "file_name")
    private String fileName;
}

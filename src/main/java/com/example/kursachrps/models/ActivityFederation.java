package com.example.kursachrps.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class ActivityFederation extends GenericEntity{

    @Column(name = "file_name")
    private String fileName;
}

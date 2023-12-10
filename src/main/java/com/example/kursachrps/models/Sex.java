package com.example.kursachrps.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sex")
@Data
public class Sex extends GenericEntity{
    @Column(name = "name")
    private String name;
}

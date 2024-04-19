package com.example.kursachrps.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sex")
@Data
public class Sex extends GenericEntity implements Comparable<Sex> {
    @Column(name = "name")
    private String name;

    @Override
    public int compareTo(Sex o) {
        return this.getName().compareTo(o.getName());
    }
}

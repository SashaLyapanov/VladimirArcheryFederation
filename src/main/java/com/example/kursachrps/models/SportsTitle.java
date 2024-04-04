package com.example.kursachrps.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "sports_titles")
public class SportsTitle extends GenericEntity{
    @Column(name = "sports_title_name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "sportsTitle", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Sportsman> sportsmanList;

    @Override
    public String toString() {
        return "Id: " + this.getId() + ", name: " + this.getName();
    }
}

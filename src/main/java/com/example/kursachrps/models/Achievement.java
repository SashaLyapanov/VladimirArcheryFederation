package com.example.kursachrps.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "achievements")
public class Achievement extends GenericEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_id", referencedColumnName = "id")
    private Competition competition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sportsman_id", referencedColumnName = "id")
    private Sportsman sportsman;

    @NotEmpty
    private int place;

    @Override
    public String toString() {
        return "competition: " + competition.getName() + " , place: " + place;
    }
}

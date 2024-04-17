package com.example.kursachrps.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "applications")
public class Application extends GenericEntity{
    @ManyToOne
    @JoinColumn(name = "sportsman_id", referencedColumnName = "id")
    private Sportsman sportsman;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_id", referencedColumnName = "id")
    private Competition competition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bow_type_id", referencedColumnName = "id")
    private BowType bowType;

    @Override
    public String toString() {
        return "Application with id: " + getId();
    }
}

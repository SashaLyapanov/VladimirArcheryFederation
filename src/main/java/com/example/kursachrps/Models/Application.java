package com.example.kursachrps.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "application")
public class Application {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sportsman_id", referencedColumnName = "id")
    private Sportsman sportsman;

    @ManyToOne
    @JoinColumn(name = "competition_id", referencedColumnName = "id")
    private Competition competition;


    @Column(name = "payment")
    private boolean payment;

    @ManyToOne
    @JoinColumn(name = "bow_tipe_id", referencedColumnName = "id")
    private BowType bowType;
}

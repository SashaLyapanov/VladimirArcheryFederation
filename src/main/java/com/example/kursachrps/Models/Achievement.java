package com.example.kursachrps.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Data
@Entity
@Table(name = "achievements")
public class Achievement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "competition_id", referencedColumnName = "id")
    private Competition competition;

    @ManyToOne
    @JoinColumn(name = "sportsman_id", referencedColumnName = "id")
    private Sportsman sportsman;

    @NotEmpty
    private int place;
}

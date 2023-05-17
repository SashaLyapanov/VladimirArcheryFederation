package com.example.kursachrps.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "applications")
public class Application {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sportsman_id", referencedColumnName = "id")
    private Sportsman sportsman;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_id", referencedColumnName = "id")
    private Competition competition;


    //В зависимости от данного поля у нас будет генерироваться протокол (Если оплата есть, то вносим в протокол). Нет, то сорян
    @Column(name = "payment")
    private boolean payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bow_type_id", referencedColumnName = "id")
    private BowType bowType;
}

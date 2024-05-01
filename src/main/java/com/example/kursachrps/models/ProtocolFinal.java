package com.example.kursachrps.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "protocol_final")
public class ProtocolFinal extends GenericEntity implements Comparable<ProtocolFinal> {
    @ManyToOne
    @JoinColumn(name = "sportsman_id", referencedColumnName = "id")
    private Sportsman sportsman;

    @ManyToOne
    @JoinColumn(name = "competition_id", referencedColumnName = "id")
    private Competition competition;

    @ManyToOne
    @JoinColumn(name = "bow_type_id", referencedColumnName = "id")
    private BowType bowType;

    //Количество очков за квалификацию
    @Column(name = "qualification_result")
    private int qualificationResult;

    //Количество очков за 1/2 финала
    @Column(name = "result_of_stage2")
    private int resultOfStage2;

    //Количество очков за данный раунд
    @Column(name = "result_of_this_stage")
    private int resultOfThisStage;

    //Место, которое занял спортсмен в квалификации
    @Column(name = "place")
    private int place;

    @Override
    public String toString() {
        return "id: " + getId() + ", sportsmanId: " + sportsman.getId() + ", competitionId:" + competition.getId();
    }

    @Override
    public int compareTo(ProtocolFinal o) {
        return -Integer.compare(this.resultOfThisStage, o.getResultOfThisStage());
    }
}

package com.example.kursachrps.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "protocol_stage4")
public class ProtocolStage4 extends GenericEntity {
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
}

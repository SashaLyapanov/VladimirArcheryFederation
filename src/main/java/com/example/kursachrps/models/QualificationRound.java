package com.example.kursachrps.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "qualifications")
public class QualificationRound extends GenericEntity implements Comparable<QualificationRound>{
    @ManyToOne
    @JoinColumn(name = "sportsman_id", referencedColumnName = "id")
    private Sportsman sportsman;

    @ManyToOne
    @JoinColumn(name = "competition_id", referencedColumnName = "id")
    private Competition competition;

    @ManyToOne
    @JoinColumn(name = "bow_type_id", referencedColumnName = "id")
    private BowType bowType;

    //Количество очков за 1 круг
    @Column(name = "dist1")
    private int dist1;

    //Количество очков за 2 круг
    @Column(name = "dist2")
    private int dist2;

    //Количество очков за 1 и 2 круг в сумме
    @Column(name = "sum")
    private int sum;

    //Количество 11 за оба круга
    @Column(name = "quantity11")
    private int quantity11;

    //Количество 10 за оба круга
    @Column(name = "quantity10")
    private int quantity10;

    //Место, которое занял спортсмен в квалификации
    @Column(name = "place")
    private int place;

    @ManyToOne
    @JoinColumn(name = "sports_title_id", referencedColumnName = "id")
    private SportsTitle sportsTitle;

    @Override
    public String toString() {
        return "id: " + getId() + ", sportsmanId: " + sportsman.getId() + ", competitionId:" + competition.getId();
    }

    @Override
    public int compareTo(QualificationRound o) {
        if (Integer.compare(this.sum, o.getSum()) != 0) {
            return -Integer.compare(this.sum, o.getSum());
        } else {
            if (Integer.compare(this.quantity11, o.getQuantity11()) != 0) {
                return -Integer.compare(this.quantity11, o.getQuantity11());
            } else {
                return -Integer.compare(this.quantity10, o.getQuantity10());
            }
        }
    }

    public int compareByPlace(QualificationRound o) {
        return Integer.compare(this.place, o.getPlace());
    }
}

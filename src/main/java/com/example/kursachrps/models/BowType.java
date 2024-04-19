package com.example.kursachrps.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "bow_types")
public class BowType extends GenericEntity implements Comparable<BowType> {
    @Column(name = "bow_type_name")
    private String bowTypeName;

    @OneToMany(mappedBy = "bowType")
    private List<Application> applicationList;

    @OneToMany(mappedBy = "bowType")
    private List<QualificationRound> qualificationRoundList;

    @ManyToMany(mappedBy = "bowTypeList")
    private List<Competition> competitionList;

    @Override
    public String toString() {
        return "BowType with id: " + getId();
    }

    @Override
    public int compareTo(BowType o) {
        return this.bowTypeName.compareTo(o.getBowTypeName());
    }
}

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

    @ManyToMany(mappedBy = "bowTypeList")
    private List<Competition> competitionList;

    @OneToMany(mappedBy = "bowType")
    private List<QualificationRound> qualificationRoundList;

    @OneToMany(mappedBy = "bowType")
    private List<ProtocolStage2> protocolStage2List;

    @OneToMany(mappedBy = "bowType")
    private List<ProtocolStage4> protocolStage4List;

    @OneToMany(mappedBy = "bowType")
    private List<ProtocolStage8> protocolStage8List;

    @OneToMany(mappedBy = "bowType")
    private List<ProtocolFinal> protocolFinalList;

    @Override
    public String toString() {
        return "BowType with id: " + getId();
    }

    @Override
    public int compareTo(BowType o) {
        return this.bowTypeName.compareTo(o.getBowTypeName());
    }
}

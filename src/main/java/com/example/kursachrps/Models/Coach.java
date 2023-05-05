package com.example.kursachrps.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="coaches")
public class Coach extends User {
    @NotEmpty(message = "У тренера обязательно должна быть квалификация")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qualification_id", referencedColumnName = "id")
    private Qualification qualification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Coach_BowTypes",
            joinColumns = @JoinColumn(name = "bow_type_id"),
            inverseJoinColumns = @JoinColumn(name = "coach_id"))
    private List<BowType> bowTypeList;

    @OneToMany(mappedBy = "personal_coach")
    private List<Sportsman> sportsmen;
}

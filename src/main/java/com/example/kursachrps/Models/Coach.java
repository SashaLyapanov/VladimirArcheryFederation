package com.example.kursachrps.Models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="coaches")
public class Coach extends User {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qualification_id", referencedColumnName = "id")
    private Qualification qualification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @JoinTable(
            name = "coach_bow_types",
            joinColumns = @JoinColumn(name = "coach_id"),
            inverseJoinColumns = @JoinColumn(name = "bow_type_id"))
    private Set<BowType> bowTypeList;

    @OneToMany(mappedBy = "personal_coach", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sportsman> sportsmen;
}

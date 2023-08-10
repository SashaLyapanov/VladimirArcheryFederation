package com.example.kursachrps.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="coaches")
public class Coach extends User {
    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region;

    @ManyToOne
    @JoinColumn(name = "sex_id", referencedColumnName = "id")
    private Sex sex;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "sports_title_id", referencedColumnName = "id")
    private SportsTitle sportsTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qualification_id", referencedColumnName = "id")
    private Qualification qualification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.MERGE})
    @JoinTable(
            name = "coach_bow_types",
            joinColumns = @JoinColumn(name = "coach_id"),
            inverseJoinColumns = @JoinColumn(name = "bow_type_id"))
    private Set<BowType> bowTypeList;

    @OneToMany(mappedBy = "personalCoach", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sportsman> sportsmen;

    @JsonIgnore
    @OneToMany(mappedBy = "coach")
    private List<Application> applicationList;
}

package com.example.kursachrps.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="coaches")
public class Coach extends User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "userName", column = @Column(name = "user_name")),
            @AttributeOverride(name = "password", column = @Column(name = "user_password")),
            @AttributeOverride(name = "role", column = @Column(name = "user_role")),
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name")),
            @AttributeOverride(name = "surname", column = @Column(name = "user_surname")),
            @AttributeOverride(name = "patronymic", column = @Column(name = "patronymic")),
            @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
    })
    private User user;


    @NotEmpty(message = "У тренера обязательно должна быть квалификация")
    @ManyToOne
    @JoinColumn(name = "qualification_id", referencedColumnName = "id")
    private Qualification qualification;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @ManyToMany
    @JoinTable(
            name = "Coach_BowType",
            joinColumns = @JoinColumn(name = "bow_type_id"),
            inverseJoinColumns = @JoinColumn(name = "coach_id"))
    private List<BowType> bowTypeList;


    @OneToMany(mappedBy = "coach")
    private List<Sportsman> sportsmans;
}

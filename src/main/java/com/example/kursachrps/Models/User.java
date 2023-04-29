package com.example.kursachrps.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    Email (UserName)
    @NotEmpty(message = "Email should not be empty")
    @Email
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    @Column(name = "user_password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @NotEmpty(message = "Name should not be empty")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Surname should not be empty")
    @Column(name = "last_name")
    private String surname;

    @NotEmpty(message = "Patronymic should not be empty")
    @Column(name = "patronymic")
    private String patronymic;

    @NotEmpty(message = "Birthday date should not be empty")
    @Column(name = "birth_date")
    private Date birthDate;



//тренера
//    @NotEmpty(message = "У тренера обязательно должна быть квалификация")
//    @ManyToOne
//    @JoinColumn(name = "qualification_id", referencedColumnName = "id")
//    private Qualification qualification;
//
//    @ManyToOne
//    @JoinColumn(name = "team_id", referencedColumnName = "id")
//    private Team team;
//
//    @ManyToMany
//    @JoinTable(
//            name = "Coach_BowTypes",
//            joinColumns = @JoinColumn(name = "bow_type_id"),
//            inverseJoinColumns = @JoinColumn(name = "coach_id"))
//    private List<BowType> bowTypeList;


//спорптсмены
//    @ManyToOne
//    @JoinColumn(name = "team_id", referencedColumnName = "id")
//    private Team team;
//
//    @ManyToOne
//    @JoinColumn(name = "coach_id", referencedColumnName = "id")
//    private User personal_coach;
//
//    @ManyToOne
//    @JoinColumn(name = "sports_title_id", referencedColumnName = "id")
//    private SportsTitle sportsTytle;
//
//    @OneToMany(mappedBy = "sportsman")
//    private List<Achievement> achievementList;
//
//    @OneToMany(mappedBy = "sportsman")
//    private List<Session> sessionList;
//
//    @OneToMany(mappedBy = "sportsman")
//    private List<Application> applicationList;
//
//    @OneToMany(mappedBy = "sportsman")
//    private List<PersonalExercise> personalExerciseList;
//
//    @ManyToMany
//    @JoinTable(
//            name = "Sportsman_Competition",
//            joinColumns = @JoinColumn(name = "sportsman_id"),
//            inverseJoinColumns = @JoinColumn(name = "competition_id"))
//    private List<Competition> competitionList;

//
//    @OneToOne(mappedBy = "user")
//    private Sportsman sportsman;
//    @OneToOne(mappedBy = "user")
//    private Coach coach;
//    @OneToOne(mappedBy = "user")
//    private Admin admin;
//    @OneToOne(mappedBy = "user")
//    private Judge judge;
//
//    public User() {
//
//    }

}

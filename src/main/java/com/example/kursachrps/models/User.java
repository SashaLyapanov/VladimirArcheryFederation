package com.example.kursachrps.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends GenericEntity{
//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;

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

    @Column(name = "birth_date")
    private Date birthDate;

    public User() {
    }

    public User(String email, String password, Role role, Status status, String firstName, String surname, String patronymic, Date birthDate) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.firstName = firstName;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
    }

}

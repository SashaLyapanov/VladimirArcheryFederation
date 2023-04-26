package com.example.kursachrps.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "judge")
public class Judge extends User{
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
}

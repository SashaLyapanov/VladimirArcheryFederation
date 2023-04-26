package com.example.kursachrps.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
@Embeddable
public class User {
//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;

    //Email (UserName)
//    @NotEmpty(message = "Email should not be empty")
//    @Email
//    @Column(name = "user_name")
    private String userName;

//    @NotEmpty(message = "Password should not be empty")
//    @Column(name = "user_password")
    private String password;

    private String role;

//    @NotEmpty(message = "Name should not be empty")
//    @Column(name = "first_name")
    private String firstName;

//    @NotEmpty(message = "Surname should not be empty")
//    @Column(name = "user_surname")
    private String surname;

//    @NotEmpty(message = "Patronymic should not be empty")
//    @Column(name = "patronymic")
    private String patronymic;

//    @NotEmpty(message = "Birthday date should not be empty")
//    @Column(name = "birth_date")
    private Date birthDate;




//    @OneToOne(mappedBy = "user")
//    private Sportsman sportsman;
//    @OneToOne(mappedBy = "user")
//    private Coach coach;
//    @OneToOne(mappedBy = "user")
//    private Admin admin;
//    @OneToOne(mappedBy = "user")
//    private Judge judge;

//    public User() {
//
//    }

}

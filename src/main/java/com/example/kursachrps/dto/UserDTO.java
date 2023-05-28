package com.example.kursachrps.dto;

import com.example.kursachrps.Models.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private int id;

    private String email;

    private String password;

    private Role role;

    private String firstName;

    private String surname;

    private String patronymic;

    private Date birthDate;
}

package com.example.kursachrps.dto;

import com.example.kursachrps.models.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private String id;

    private String email;

    private String password;

    private Role role;

    private String firstName;

    private String surname;

    private String patronymic;

    private Date birthDate;
}

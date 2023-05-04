package com.example.kursachrps.dto;

import com.example.kursachrps.Models.Role;
import com.example.kursachrps.Models.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private String email;

    private String password;

    private String firstName;

    private String surname;

    private String patronymic;

    private Date birthDate;
}

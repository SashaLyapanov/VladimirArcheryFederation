package com.example.kursachrps.dto.AuthAndRegistration;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class SignUpDTO {

    private String firstName;

    private String surname;

    private String patronymic;
    @Email
    private String email;

    private String password;
}

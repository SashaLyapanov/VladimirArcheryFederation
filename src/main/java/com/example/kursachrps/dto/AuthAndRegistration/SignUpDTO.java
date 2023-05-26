package com.example.kursachrps.dto.AuthAndRegistration;

import com.example.kursachrps.dto.AdditionalDTO.RegionDTO;
import com.example.kursachrps.dto.AdditionalDTO.SexDTO;
import com.example.kursachrps.dto.AdditionalDTO.SportsTitleDTO;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.Date;

@Data
public class SignUpDTO {

    private String firstName;

    private String surname;

    private String patronymic;
    @Email
    private String email;

    private String password;

    private RegionDTO region;

    private SexDTO sex;

    private SportsTitleDTO sportsTitle;

    private Date birthDate;
}

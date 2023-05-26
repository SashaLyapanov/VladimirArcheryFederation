package com.example.kursachrps.dto;

import com.example.kursachrps.dto.AdditionalDTO.RegionDTO;
import com.example.kursachrps.dto.AdditionalDTO.SexDTO;
import com.example.kursachrps.dto.AdditionalDTO.SportsTitleDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SportsmanDTO {

    private int id;

    private String email;

    private String password;

    private RegionDTO region;

    private SexDTO sex;

    private String firstName;

    private String surname;

    private String patronymic;

    private SportsTitleDTO sportsTitle;

    private Date birthDate;
}

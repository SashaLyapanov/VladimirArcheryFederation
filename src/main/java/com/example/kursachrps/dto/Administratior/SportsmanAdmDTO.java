package com.example.kursachrps.dto.Administratior;

import com.example.kursachrps.dto.AdditionalDTO.RegionDTO;
import com.example.kursachrps.dto.AdditionalDTO.SexDTO;
import com.example.kursachrps.dto.AdditionalDTO.SportsTitleDTO;
import com.example.kursachrps.dto.AdditionalDTO.TeamDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SportsmanAdmDTO {

    private String email;

    private String firstName;

    private String surname;

    private String patronymic;

    private RegionDTO region;

    private SexDTO sex;

    private TeamDTO team;

    private SportsTitleDTO sportsTitle;

    private CoachAdmDTO personal_coach;

    private Date birthDate;

}

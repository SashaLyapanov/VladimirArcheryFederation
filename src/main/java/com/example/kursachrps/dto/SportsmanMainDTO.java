package com.example.kursachrps.dto;

import com.example.kursachrps.dto.AdditionalDTO.SportsTitleDTO;
import com.example.kursachrps.dto.AdditionalDTO.TeamDTO;
import com.example.kursachrps.dto.Administratior.CoachAdmDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SportsmanMainDTO {

    private int id;

    private String email;

    private String password;

    private String firstName;

    private String surname;

    private String patronymic;

    private Date birthDate;

    private TeamDTO team;

    //Данное поле будет отображаться только у Администратора, чтобы он по результатам соревнований мог изменять разряд спортсмена
    private SportsTitleDTO sportsTitle;

    private CoachAdmDTO personal_coach;
}

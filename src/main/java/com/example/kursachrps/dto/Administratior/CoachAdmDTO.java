package com.example.kursachrps.dto.Administratior;

import com.example.kursachrps.dto.AdditionalDTO.QualificationDTO;
import com.example.kursachrps.dto.AdditionalDTO.TeamDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoachAdmDTO {

    private String firstName;

    private String surname;

    private String patronymic;

    private TeamDTO team;

    private QualificationDTO qualification;

}

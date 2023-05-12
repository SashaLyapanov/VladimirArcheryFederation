package com.example.kursachrps.dto;

import com.example.kursachrps.Models.BowType;
import com.example.kursachrps.dto.AdditionalDTO.BowTypeDTO;
import com.example.kursachrps.dto.AdditionalDTO.QualificationDTO;
import com.example.kursachrps.dto.AdditionalDTO.TeamDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoachDTO {

    private String email;

    private String password;

    private String firstName;

    private String surname;

    private String patronymic;

    private Date birthDate;

    private Set<BowTypeDTO> bowTypeList;

    private QualificationDTO qualification;

    private TeamDTO team;

}

package com.example.kursachrps.dto;

import com.example.kursachrps.dto.AdditionalDTO.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
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

    private RegionDTO region;

    private SexDTO sex;

    private SportsTitleDTO sportsTitle;

    private Date birthDate;

    private Set<BowTypeDTO> bowTypeList;

    private QualificationDTO qualification;

    private TeamDTO team;

}

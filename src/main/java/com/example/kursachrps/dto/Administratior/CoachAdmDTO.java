package com.example.kursachrps.dto.Administratior;

import com.example.kursachrps.dto.AdditionalDTO.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoachAdmDTO {

    private String id;

    private String email;

    private String firstName;

    private String surname;

    private String patronymic;

    private RegionDTO region;

    private SexDTO sex;

    private SportsTitleDTO sportsTitle;

    private Date birthDate;

    private TeamDTO team;

    private QualificationDTO qualification;

    private List<BowTypeDTO> bowTypeList;
}

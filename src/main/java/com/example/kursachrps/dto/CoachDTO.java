package com.example.kursachrps.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoachDTO {

    private String firstName;
    private String surname;
    private String patronymic;

}

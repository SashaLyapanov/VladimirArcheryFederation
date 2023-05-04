package com.example.kursachrps.dto;

import com.example.kursachrps.Models.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SportsmanDTO {

    private String firstName;

    private String surname;

    private String patronymic;

    private String teamName;

    private String sportsTitle;

    private String coachName;

//    Нужно сделать реализацию для подтягивания данных из User
//    private Date birthDate;
}

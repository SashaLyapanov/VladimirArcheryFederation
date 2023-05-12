package com.example.kursachrps.dto.AdditionalDTO;

import com.example.kursachrps.Models.Application;
import com.example.kursachrps.Models.Coach;
import com.example.kursachrps.Models.Competition;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BowTypeDTO {

    private int id;
    private String bowTypeName;

}

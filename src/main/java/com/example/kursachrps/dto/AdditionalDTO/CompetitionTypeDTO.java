package com.example.kursachrps.dto.AdditionalDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompetitionTypeDTO {
    private int id;
    private String name;
}

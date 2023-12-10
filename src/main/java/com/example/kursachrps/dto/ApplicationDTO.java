package com.example.kursachrps.dto;

import com.example.kursachrps.dto.AdditionalDTO.BowTypeDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationDTO {

    private String id;

    private boolean payment;

    private BowTypeDTO bowType;

    private CompetitionCreateDTO competition;

    private SportsmanDTO sportsman;
}

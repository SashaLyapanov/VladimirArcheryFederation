package com.example.kursachrps.dto;


import com.example.kursachrps.dto.AdditionalDTO.BowTypeDTO;
import com.example.kursachrps.dto.AdditionalDTO.CategoryDTO;
import com.example.kursachrps.dto.AdditionalDTO.CompetitionTypeDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompetitionDTO {

    private String name;
    private String place;
    private CompetitionTypeDTO type;
    private List<CategoryDTO> categories;
    private List<BowTypeDTO> bowTypeList;
    private String mainJudge;
    private String secretary;
    private String zamJudge;
    private String judges;
    private Date date;

}

package com.example.kursachrps.dto;

import com.example.kursachrps.dto.AdditionalDTO.BowTypeDTO;
import com.example.kursachrps.dto.AdditionalDTO.CategoryDTO;
import com.example.kursachrps.dto.AdditionalDTO.CompetitionTypeDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompetitionCreateDTO {

    private String id;
    private String name;
    private String place;
    private CompetitionTypeDTO type;
    private int price;
    private List<CategoryDTO> categories;
    private Set<BowTypeDTO> bowTypeList;
    private String mainJudge;
    private String secretary;
    private String zamJudge;
    private String judges;
    private Date date;

}

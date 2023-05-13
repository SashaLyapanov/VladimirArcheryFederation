package com.example.kursachrps.dto;

import com.example.kursachrps.Models.CompetitionType;
import com.example.kursachrps.dto.AdditionalDTO.CategoryDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CompetitionDTO {

    private String name;
    private String place;
    private CompetitionType type;
    private List<CategoryDTO> categories;
    private Date date;

}

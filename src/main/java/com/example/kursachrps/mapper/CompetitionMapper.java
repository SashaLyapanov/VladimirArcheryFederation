package com.example.kursachrps.mapper;


import com.example.kursachrps.Models.Category;
import com.example.kursachrps.Models.Competition;
import com.example.kursachrps.Models.CompetitionType;
import com.example.kursachrps.dto.AdditionalDTO.CategoryDTO;
import com.example.kursachrps.dto.AdditionalDTO.CompetitionTypeDTO;
import com.example.kursachrps.dto.CompetitionDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {

    CompetitionDTO fromCompetition(Competition competition);

    List<CompetitionDTO> fromCompetition(List<Competition> competition);

    Competition fromCompetitionDTO(CompetitionDTO competitionDTO);

    List<Competition> fromCompetitionDTO(List<CompetitionDTO> competitionDTOList);

    CompetitionType fromCompetitionTypeDTO(CompetitionTypeDTO competitionTypeDTO);

    CompetitionTypeDTO fromCompetition(CompetitionType competitionType);

    Category fromCategoryDTO(CategoryDTO categoryDTO);

    List<Category> fromCategoryDTO(List<CategoryDTO> categories);

    CategoryDTO fromCategory(Category category);

    List<CategoryDTO> fromCategory(List<Category> categories);

}

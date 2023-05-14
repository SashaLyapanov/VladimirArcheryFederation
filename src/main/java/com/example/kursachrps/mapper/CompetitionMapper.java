package com.example.kursachrps.mapper;


import com.example.kursachrps.Models.Competition;
import com.example.kursachrps.Models.CompetitionType;
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

}

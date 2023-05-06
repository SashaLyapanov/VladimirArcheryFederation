package com.example.kursachrps.mapper;

import com.example.kursachrps.Models.Competition;
import com.example.kursachrps.dto.CompetitionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {

    @Mappings({
            @Mapping(source = "name", target = "title"),
    })
    CompetitionDTO fromCompetition(Competition competition);

    @Mappings({
            @Mapping(source = "name", target = "title"),
    })
    List<CompetitionDTO> fromCompetition(List<Competition> competition);

}

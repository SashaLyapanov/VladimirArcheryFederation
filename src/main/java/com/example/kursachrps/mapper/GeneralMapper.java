package com.example.kursachrps.mapper;

import com.example.kursachrps.models.*;
import com.example.kursachrps.dto.AdditionalDTO.*;
import com.example.kursachrps.dto.NewDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GeneralMapper {
    RegionDTO fromRegion(Region region);
    List<RegionDTO> fromRegion (List<Region> regions);

    SportsTitleDTO fromSportsTitle(SportsTitle sportsTitle);
    List<SportsTitleDTO> fromSportsTitle(List<SportsTitle> sportsTitle);

    List<QualificationDTO> fromQualification(List<Qualification> qualifications);

    List<TeamDTO> fromTeam(List<Team> teams);

    List<BowTypeDTO> fromBowType(List<BowType> bowTypes);

    List<New> fromNewsDTO(List<NewDTO> newDTOS);

    List<NewDTO> fromNews(List<New> news);

    New fromNewDTO(NewDTO newDTO);

    NewDTO fromNew(New new1);
}

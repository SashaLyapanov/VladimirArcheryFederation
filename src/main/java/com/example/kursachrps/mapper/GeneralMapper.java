package com.example.kursachrps.mapper;

import com.example.kursachrps.Models.Region;
import com.example.kursachrps.Models.SportsTitle;
import com.example.kursachrps.dto.AdditionalDTO.RegionDTO;
import com.example.kursachrps.dto.AdditionalDTO.SportsTitleDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GeneralMapper {
    RegionDTO fromRegion(Region region);
    List<RegionDTO> fromRegion (List<Region> regions);

    SportsTitleDTO fromSportsTitle(SportsTitle sportsTitle);
    List<SportsTitleDTO> fromSportsTitle(List<SportsTitle> sportsTitle);
}

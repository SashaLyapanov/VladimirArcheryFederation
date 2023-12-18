package com.example.kursachrps.mapper;

import com.example.kursachrps.models.*;
import com.example.kursachrps.dto.AdditionalDTO.*;
import com.example.kursachrps.dto.CompetitionDTO;
import com.example.kursachrps.dto.ArticleDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GeneralMapper {
    RegionDTO fromRegion(Region region);
    List<RegionDTO> fromRegion (List<Region> regions);

    SportsTitleDTO fromSportsTitle(SportsTitle sportsTitle);
    List<SportsTitleDTO> fromSportsTitle(List<SportsTitle> sportsTitle);

    List<BowTypeDTO> fromBowType(List<BowType> bowTypes);

    List<CategoryDTO> fromCategory(List<Category> categories);

    List<SexDTO> fromSex(List<Sex> sex);

    List<CompetitionTypeDTO> fromCompetitionType(List<CompetitionType> competitionTypes);

    List<Sex> fromSexDTO(List<SexDTO> sex);

    List<Article> fromArticleDTO(List<ArticleDTO> articleDTOS);

    List<ArticleDTO> fromArticle(List<Article> articles);

    Article fromArticleDTO(ArticleDTO articleDTO);

    ArticleDTO fromArticle(Article article1);

    Competition fromCompetitionDTO(CompetitionDTO competitionDTO);

    List<Competition> fromCompetitionDTO(List<CompetitionDTO> competitionDTOList);

    CompetitionDTO fromCompetition(Competition competition);

    List<CompetitionDTO> fromCompetition(List<Competition> competitions);
}

package com.example.kursachrps.mapper;

import com.example.kursachrps.Models.*;
import com.example.kursachrps.dto.AdditionalDTO.BowTypeDTO;
import com.example.kursachrps.dto.AdditionalDTO.QualificationDTO;
import com.example.kursachrps.dto.AdditionalDTO.SportsTitleDTO;
import com.example.kursachrps.dto.AdditionalDTO.TeamDTO;
import com.example.kursachrps.dto.Administratior.CoachAdmDTO;
import com.example.kursachrps.dto.Administratior.SportsmanAdmDTO;
import com.example.kursachrps.dto.CoachDTO;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO transform(User user);

    List<UserDTO> fromUser(List<User> users);

    User convert(UserDTO user);

    BowType fromBowTypeDTO(BowTypeDTO value);

    BowTypeDTO fromBowType(BowType value);
    List<BowTypeDTO> fromBowType(List<BowType> value);

    SportsTitleDTO fromSportsTitle(SportsTitle sportsTitle);

    SportsTitle fromSportsTitleDTO(SportsTitleDTO sportsTitleDTO);

    TeamDTO fromTeam(Team team);

    Team fromTeamDTO(TeamDTO teamDTO);

    QualificationDTO fromQualification(Qualification qualification);

    Qualification fromQualificationDTO(QualificationDTO qualificationDTO);

    Sportsman fromUserDTO(UserDTO userDTO);

    @Mappings({
            @Mapping(target = "team", source = "team"),
            @Mapping(target = "sportsTitle", source = "sportsTitle"),
            @Mapping(target = "personal_coach", source = "personal_coach")
    })
    SportsmanAdmDTO fromSportsman(Sportsman sportsman);

    @Mappings({
            @Mapping(target = "team", source = "team"),
            @Mapping(target = "sportsTitle", source = "sportsTitle"),
            @Mapping(target = "personal_coach", source = "personal_coach")
    })
    List<SportsmanAdmDTO> fromSportsmanList(List<Sportsman> sportsmen);

    Sportsman fromSportsmanAdmDTO(SportsmanAdmDTO sportsmanAdmDTO);


    Sportsman fromSportsmanDTO(SportsmanDTO sportsmanDTO);

    @Mappings(
            @Mapping(source = "id", target = "id")
    )
    CoachAdmDTO fromCoach(Coach coach);

    @Mappings(
            @Mapping(source = "id", target = "id")
    )
    List<CoachAdmDTO> fromCoachList(List<Coach> coaches);

    @Mappings(
            @Mapping(source = "id", target = "id")
    )
    Coach fromcoachAdmDTO(CoachAdmDTO coachAdmDTO);

    @Mappings(
            @Mapping(source = "id", target = "id")
    )
    List<Coach> fromcoachAdmDTO(List<CoachAdmDTO> coachAdmDTO);

    Coach fromCoachDTO(CoachDTO coachDTO);

}

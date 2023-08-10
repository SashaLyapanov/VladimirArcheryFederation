package com.example.kursachrps.mapper;

import com.example.kursachrps.models.*;
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
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO transform(User user);

    List<UserDTO> fromUser(List<User> users);

    User convert(UserDTO user);

    BowType fromBowTypeDTO(BowTypeDTO value);
    Set<BowType> fromBowTypeDTO(Set<BowTypeDTO> bowTypeDTOSet);

    BowTypeDTO fromBowType(BowType value);
    Set<BowTypeDTO> fromBowType(Set<BowType> value);

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
            @Mapping(target = "personalCoach", source = "personalCoach")
    })
    SportsmanAdmDTO fromSportsman(Sportsman sportsman);

    @Mappings({
            @Mapping(target = "team", source = "team"),
            @Mapping(target = "sportsTitle", source = "sportsTitle"),
            @Mapping(target = "personalCoach", source = "personalCoach")
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

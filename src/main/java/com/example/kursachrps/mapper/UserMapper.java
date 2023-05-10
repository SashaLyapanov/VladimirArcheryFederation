package com.example.kursachrps.mapper;

import com.example.kursachrps.Models.BowType;
import com.example.kursachrps.Models.Coach;
import com.example.kursachrps.Models.Sportsman;
import com.example.kursachrps.Models.User;
import com.example.kursachrps.dto.AdditionalDTO.BowTypeDTO;
import com.example.kursachrps.dto.Administratior.CoachAdmDTO;
import com.example.kursachrps.dto.Administratior.SportsmanAdmDTO;
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


    CoachAdmDTO fromCoach(Coach coach);

    List<CoachAdmDTO> fromCoachList(List<Coach> coaches);

    Coach fromcoachAdmDTO(CoachAdmDTO coachAdmDTO);

    List<Coach> fromcoachAdmDTO(List<CoachAdmDTO> coachAdmDTO);


}

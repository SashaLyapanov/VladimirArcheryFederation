package com.example.kursachrps.mapper;

import com.example.kursachrps.models.*;
import com.example.kursachrps.dto.AdditionalDTO.BowTypeDTO;
import com.example.kursachrps.dto.AdditionalDTO.SportsTitleDTO;
import com.example.kursachrps.dto.Administratior.SportsmanAdmDTO;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "firstName", target = "name")
    UserDTO transform(User user);

    List<UserDTO> fromUser(List<User> users);

    User convert(UserDTO user);

    BowType fromBowTypeDTO(BowTypeDTO value);
    Set<BowType> fromBowTypeDTO(Set<BowTypeDTO> bowTypeDTOSet);

    BowTypeDTO fromBowType(BowType value);
    Set<BowTypeDTO> fromBowType(Set<BowType> value);

    SportsTitleDTO fromSportsTitle(SportsTitle sportsTitle);

    SportsTitle fromSportsTitleDTO(SportsTitleDTO sportsTitleDTO);

    Sportsman fromUserDTO(UserDTO userDTO);

    @Mappings({
            @Mapping(target = "sportsTitle", source = "sportsTitle")
    })
    SportsmanAdmDTO fromSportsman(Sportsman sportsman);

    @Mappings({
            @Mapping(target = "sportsTitle", source = "sportsTitle")
    })
    List<SportsmanAdmDTO> fromSportsmanList(List<Sportsman> sportsmen);

    Sportsman fromSportsmanAdmDTO(SportsmanAdmDTO sportsmanAdmDTO);


    Sportsman fromSportsmanDTO(SportsmanDTO sportsmanDTO);

}

package com.example.kursachrps.mapper;

import com.example.kursachrps.Models.Sportsman;
import com.example.kursachrps.Models.User;
import com.example.kursachrps.dto.Administratior.SportsmanAdmDTO;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
//    @Mapping(target="birthDate", source="birthDate",
//            dateFormat="dd-MM-yyyy HH:mm:ss")
    UserDTO transform(User user);

//    @Mapping(target="birthDate", source="birthDate",
//            dateFormat="dd-MM-yyyy HH:mm:ss")
    User convert(UserDTO user);

    List<UserDTO> fromUser(List<User> users);


//    UserDTO fromSportsman(Sportsman sportsman);

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

}

package com.example.kursachrps.mapper;

import com.example.kursachrps.models.Sportsman;
import com.example.kursachrps.models.User;
import com.example.kursachrps.dto.AuthAndRegistration.SignUpDTO;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.dto.SportsmanMainDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SportsmanMapper {

    SportsmanMainDTO fromSportsman(Sportsman sportsman);

    List<SportsmanMainDTO> fromSportsman(List<Sportsman> sportsmen);

    Sportsman fromSportsmanMainDTO(SportsmanMainDTO sportsmanMainDTO);

    List<Sportsman> fromSportsmanMainDTO(List<SportsmanMainDTO> sportsmanMainDTOList);

    Sportsman fromSignUpDTO(SignUpDTO signUpDTO);

    SignUpDTO fromSportsmanForAuth(Sportsman sportsman);

    Sportsman fromUser(User user);

    List<SportsmanDTO> fromSportsmanToSportsmanDTO(List<Sportsman> sportsmen);
}

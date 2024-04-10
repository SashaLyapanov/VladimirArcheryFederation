package com.example.kursachrps.mapper;

import com.example.kursachrps.models.Sportsman;
import com.example.kursachrps.models.User;
import com.example.kursachrps.dto.AuthAndRegistration.SignUpDTO;
import com.example.kursachrps.dto.SportsmanDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SportsmanMapper {

    SportsmanDTO fromSportsman(Sportsman sportsman);

    List<SportsmanDTO> fromSportsman(List<Sportsman> sportsmen);

    Sportsman fromSportsmanDTO(SportsmanDTO sportsmanDTO);

    List<Sportsman> fromSportsmanDTO(List<SportsmanDTO> sportsmanDTOList);

    Sportsman fromSignUpDTO(SignUpDTO signUpDTO);

    SignUpDTO fromSportsmanForAuth(Sportsman sportsman);

    Sportsman fromUser(User user);

    List<SportsmanDTO> fromSportsmanToSportsmanDTO(List<Sportsman> sportsmen);
}

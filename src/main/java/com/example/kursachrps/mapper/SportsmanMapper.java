//package com.example.kursachrps.mapper;
//
//import com.example.kursachrps.Models.*;
//import com.example.kursachrps.dto.CoachDTO;
//import com.example.kursachrps.dto.SportsmanDTO;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Mappings;
//
//@Mapper(componentModel = "spring")
//public interface SportsmanMapper {
//
////
////    @Mappings({
////            @Mapping(source = "coachName", target = "firstName"),
////    })
////    CoachDTO convertCoach(SportsmanDTO sportsmanDTO);
////
////    @Mappings({
////            @Mapping(source = "firstName", target = "coachName"),
////    })
////    SportsmanDTO convertCoach(CoachDTO coach);
////
////    @Mappings({
////            @Mapping(source = "teamName", target = "name"),
////    })
////    Team convertTeam(SportsmanDTO sportsmanDTO);
////
////    @Mappings({
////            @Mapping(source = "name", target = "teamName"),
////    })
////    SportsmanDTO convertTeam(Team team);
////
////    @Mappings({
////            @Mapping(source = "sportsTitle", target = "name"),
////    })
////    SportsTitle convertSportsTitle(SportsmanDTO sportsmanDTO);
////
////    @Mappings({
////            @Mapping(source = "name", target = "sportsTitle"),
////    })
////    String convertSportsTitle(SportsTitle sportsTitle);
////
////
////    default SportsmanDTO convert(Sportsman sportsman) {
////
////        SportsmanDTO sportsmanDTO = new SportsmanDTO();
////
////        sportsmanDTO.setFirstName();
////        sportsmanDTO.setSurname();
////        sportsmanDTO.setPatronymic();
////        sportsmanDTO.setTeamName();
////        sportsmanDTO.setCoachName(convertCoach(sportsman.getPersonal_coach()));
////        sportsmanDTO.setSportsTitle(convertSportsTitle(sportsman.getSportsTytle()));
////    }
//
//}

package com.example.kursachrps.mapper;

import com.example.kursachrps.Models.*;
import com.example.kursachrps.dto.diary.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiaryMapper {
    @Mappings({
            @Mapping(source = "date_time", target = "date"),
    })
    List<SessionDTO> fromSession(List<Session> sessions);

    @Mappings ({
            @Mapping(source = "sessionExercisesList",target = "sessionExercises"),
    })
    GetExercisesDTO fromExercises(Session sessionExercises);

    Session convert(CreateSessionDTO session);

    ExerciseDTO fromExercise(Exercise exercise);

    List<ExerciseDTO> fromExerciseList(List<Exercise> exerciseList);

    Exercise fromExerciseDTO(ExerciseDTO exerciseDTO);

    PersonalExerciseDTO fromPersonalExercise(PersonalExercise personalExercise);

    List<PersonalExerciseDTO> fromPersonalExercise(List<PersonalExercise> personalExercises);

    PersonalExercise fromPersonalExerciseDTO(PersonalExerciseDTO personalExerciseDTO);

    SessionExercises fromSessionExercisesDTO(SessionExercisesDTO sessionExercisesDTO);

    List<SessionTypeDTO> fromSessionType(List<SessionType> sessionTypes);

}
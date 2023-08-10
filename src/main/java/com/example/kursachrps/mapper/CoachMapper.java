package com.example.kursachrps.mapper;

import com.example.kursachrps.models.Coach;
import com.example.kursachrps.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoachMapper {

    Coach fromUser(User user);

}

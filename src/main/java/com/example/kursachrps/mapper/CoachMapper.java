package com.example.kursachrps.mapper;

import com.example.kursachrps.Models.Coach;
import com.example.kursachrps.Models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoachMapper {

    Coach fromUser(User user);

}

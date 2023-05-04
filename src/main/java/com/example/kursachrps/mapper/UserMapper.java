package com.example.kursachrps.mapper;

import com.example.kursachrps.Models.User;
import com.example.kursachrps.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
//    @Mapping(target="birthDate", source="birthDate",
//            dateFormat="dd-MM-yyyy HH:mm:ss")
    UserDTO transform(User user);

//    @Mapping(target="birthDate", source="birthDate",
//            dateFormat="dd-MM-yyyy HH:mm:ss")
    User convert(UserDTO user);

}

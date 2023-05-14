package com.example.kursachrps.mapper;

import com.example.kursachrps.Models.Application;
import com.example.kursachrps.dto.ApplicationDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    Application fromApplicationDTO(ApplicationDTO applicaitonDTO);

    List<Application> fromApplicationDTO(List<ApplicationDTO> applicationDTOS);

    ApplicationDTO fromApplication(Application application);

    List<ApplicationDTO> fromApplication(List<Application> applications);


}

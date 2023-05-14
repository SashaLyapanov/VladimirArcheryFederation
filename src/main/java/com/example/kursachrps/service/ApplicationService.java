package com.example.kursachrps.service;

import com.example.kursachrps.Models.Application;
import com.example.kursachrps.dto.ApplicationDTO;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }


    /**
     * Метод для выборки всех заявок для определенных соревнований
     */
    @Transactional
    public List<Application> getApplicationsForCompetition(int competitionId) {
        return applicationRepository.findApplicationByCompetitionId(competitionId);
    }

    /**
     * Метод для выборки всех спортсменов из списка заявок
     */
    @Transactional
    public List<SportsmanDTO> getSportsmenFromApplications(List<ApplicationDTO> applicationDTOList) {
        List<SportsmanDTO> sportsmanDTOList = new ArrayList<>();

        for(ApplicationDTO applicationDTO: applicationDTOList) {
            sportsmanDTOList.add(applicationDTO.getSportsman());
        }
        return sportsmanDTOList;
    }

}

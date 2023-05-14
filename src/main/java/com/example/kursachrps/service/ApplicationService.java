package com.example.kursachrps.service;

import com.example.kursachrps.Models.Application;
import com.example.kursachrps.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    };


    /**
     * Метод для выборки всех заявок для определенных соревнований
     */
    public List<Application> getApplicationsForCompetition(int competitionId) {
        return applicationRepository.findApplicationByCompetitionId(competitionId);
    }




}

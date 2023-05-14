package com.example.kursachrps.service;

import com.example.kursachrps.Models.Application;
import com.example.kursachrps.Models.Competition;
import com.example.kursachrps.Models.Sportsman;
import com.example.kursachrps.repositories.ApplicationRepository;
import com.example.kursachrps.repositories.CompetitionRepository;
import com.example.kursachrps.repositories.SportsmanMainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SportsmanService {

    private final CompetitionRepository competitionRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationService applicationService;
    private final SportsmanMainRepository sportsmanMainRepository;

    @Autowired
    public SportsmanService(CompetitionRepository competitionRepository,
                            ApplicationRepository applicationRepository,
                            ApplicationService applicationService, SportsmanMainRepository sportsmanMainRepository) {
        this.competitionRepository = competitionRepository;
        this.applicationRepository = applicationRepository;
        this.applicationService = applicationService;
        this.sportsmanMainRepository = sportsmanMainRepository;
    }


    /**
     * Метод для регистрации спортсмена на соревнования
     */
    @Transactional
    public void registrateSportsman(int sportsmanId, int competitionId, Application application) {

        Competition competition = competitionRepository.findById(competitionId).orElse(null);
        Sportsman sportsman = sportsmanMainRepository.findById(sportsmanId).orElse(null);

        application.setCompetition(competition);
        application.setSportsman(sportsman);
        applicationRepository.save(application);

    }



}





























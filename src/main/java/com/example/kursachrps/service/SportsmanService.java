package com.example.kursachrps.service;

import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.mapper.ApplicationMapper;
import com.example.kursachrps.models.*;
import com.example.kursachrps.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class SportsmanService {

    private final CompetitionRepository competitionRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationService applicationService;
    private final ApplicationMapper applicationMapper;
    private final SportsmanRepository sportsmanRepository;


    @Autowired
    public SportsmanService(CompetitionRepository competitionRepository,
                            ApplicationRepository applicationRepository,
                            ApplicationService applicationService,
                            ApplicationMapper applicationMapper,
                            SportsmanRepository sportsmanRepository) {
        this.competitionRepository = competitionRepository;
        this.applicationRepository = applicationRepository;
        this.applicationService = applicationService;
        this.applicationMapper = applicationMapper;
        this.sportsmanRepository = sportsmanRepository;
    }


    /**
     * Метод для регистрации спортсмена на соревнования
     */
    @Transactional
    public void registrateSportsman(String sportsmanId, String competitionId, Application application) {
        if (application.getBowType().getId() == null ) {
            return;
        }
        Competition competition = competitionRepository.findById(competitionId).orElse(null);
        Sportsman sportsman = sportsmanRepository.findById(sportsmanId).orElse(null);

        application.setCompetition(competition);
        application.setSportsman(sportsman);
        applicationRepository.save(application);
    }

    /**
     * Метод для получения всех спортсменов по Соревнованию и Типу лука
     */
    public List<SportsmanDTO> getAllSportmanByCompetitionAndBowType(String competitionId, String bowTypeName) {
        if (Objects.equals(bowTypeName, "all")) {
            return applicationService.getSportsmenDTOFromApplications(applicationMapper.fromApplication(applicationService.getApplicationsForCompetition(competitionId)));
        } else {
            return applicationService.getSportsmenDTOFromApplications(applicationMapper.fromApplication(applicationService.getApplicationsForCompetitionAndBowType(competitionId, bowTypeName)));
        }
    }

    public List<Sportsman> getAllSportsmanByCompetition(String competitionId) {
        if (competitionId == null || competitionId.equals("")) {
            return null;
        } else {
            List<Sportsman> sportsmanList = applicationService.getSportsmenFromApplications(applicationService.getApplicationsForCompetition(competitionId));
            return sportsmanList;
        }
    }

}






























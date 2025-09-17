package com.example.kursachrps.service;

import com.example.kursachrps.dto.ApplicationDTO;
import com.example.kursachrps.mapper.ApplicationMapper;
import com.example.kursachrps.models.*;
import com.example.kursachrps.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        if (application.getBowType().getId() == null) {
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
    public List<ApplicationDTO> getAllSportmanByCompetitionAndBowType(String competitionId, String bowTypeName) {
        List<ApplicationDTO> response;
        if (Objects.equals(bowTypeName, "all")) {
            response = applicationMapper.fromApplication(applicationService.getApplicationsForCompetition(competitionId));
        } else {
            response = applicationMapper.fromApplication(applicationService.getApplicationsForCompetitionAndBowType(competitionId, bowTypeName));
        }
        Comparator<ApplicationDTO> compareByBowTypeName = Comparator
                .comparing(dto -> dto.getBowType().getBowTypeName());

        return response.stream()
                .sorted(compareByBowTypeName)
                .collect(Collectors.toList());
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






























package com.example.kursachrps.service;

import com.example.kursachrps.models.Application;
import com.example.kursachrps.models.Coach;
import com.example.kursachrps.models.Competition;
import com.example.kursachrps.models.Sportsman;
import com.example.kursachrps.repositories.ApplicationRepository;
import com.example.kursachrps.repositories.CoachMainRepository;
import com.example.kursachrps.repositories.CompetitionRepository;
import com.example.kursachrps.repositories.RegistrAndAuth.CoachRepository;
import com.example.kursachrps.repositories.SportsmanMainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CoachService {

    private CoachMainRepository coachMainRepository;
    private CoachRepository coachRepository;
    private SportsmanMainRepository sportsmanMainRepository;
    private CompetitionRepository competitionRepository;
    private ApplicationRepository applicationRepository;

    @Autowired
    public CoachService(CoachMainRepository coachMainRepository,
                        CoachRepository coachRepository,
                        SportsmanMainRepository sportsmanMainRepository,
                        CompetitionRepository competitionRepository,
                        ApplicationRepository applicationRepository) {
        this.coachMainRepository = coachMainRepository;
        this.coachRepository = coachRepository;
        this.sportsmanMainRepository = sportsmanMainRepository;
        this.competitionRepository = competitionRepository;
        this.applicationRepository = applicationRepository;
    }


    public List<Sportsman> findAllMySportsmen(String coachEmail) {
        Coach coach = coachMainRepository.findByEmail(coachEmail).orElse(null);
        assert coach != null;
        return sportsmanMainRepository.findAllSportsmanByPersonalCoach(coach.getId());
    }

    /**
     * Метод для регистрации спортсмена на соревнования
     */
    @Transactional
    public void registrateCoach(String coachId, String  competitionId, Application application) {

        Competition competition = competitionRepository.findById(competitionId).orElse(null);
        Coach coach = coachMainRepository.findById(coachId).orElse(null);

        application.setCompetition(competition);
        application.setCoach(coach);
        applicationRepository.save(application);
    }

}

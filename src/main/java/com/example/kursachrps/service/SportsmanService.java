package com.example.kursachrps.service;

import com.example.kursachrps.models.*;
import com.example.kursachrps.dto.SportsmanMainDTO;
import com.example.kursachrps.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SportsmanService {

    private final CompetitionRepository competitionRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationService applicationService;
    private final SportsmanMainRepository sportsmanMainRepository;
    private final CoachMainRepository coachMainRepository;
    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SportsmanService(CompetitionRepository competitionRepository,
                            ApplicationRepository applicationRepository,
                            ApplicationService applicationService,
                            SportsmanMainRepository sportsmanMainRepository,
                            CoachMainRepository coachMainRepository,
                            TeamRepository teamRepository,
                            PasswordEncoder passwordEncoder) {
        this.competitionRepository = competitionRepository;
        this.applicationRepository = applicationRepository;
        this.applicationService = applicationService;
        this.sportsmanMainRepository = sportsmanMainRepository;
        this.coachMainRepository = coachMainRepository;
        this.teamRepository = teamRepository;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * Метод для регистрации спортсмена на соревнования
     */
    @Transactional
    public void registrateSportsman(String sportsmanId, String competitionId, Application application) {

        Competition competition = competitionRepository.findById(competitionId).orElse(null);
        Sportsman sportsman = sportsmanMainRepository.findById(sportsmanId).orElse(null);
        Coach coach = coachMainRepository.findBySportsmen(sportsman).orElse(null);

        application.setCompetition(competition);
        application.setSportsman(sportsman);
        application.setCoach(coach);
        applicationRepository.save(application);

    }

    @Transactional
    public SportsmanMainDTO hashPassword(SportsmanMainDTO sportsmanDTO) {
        sportsmanDTO.setPassword(passwordEncoder.encode(sportsmanDTO.getPassword()));
        return sportsmanDTO;
    }

    /**
     * Метод для редактирования собственного профиля у спортсмена
     */
    @Transactional
    public Sportsman editProfile(int id, Sportsman updatedSportsman) {

        Sportsman sportsman = sportsmanMainRepository.findById(id).orElse(null);

        assert sportsman != null;
        if (updatedSportsman.getPassword() != null) {
            sportsman.setPassword(updatedSportsman.getPassword());
        }
        sportsman.setFirstName(updatedSportsman.getFirstName());
        sportsman.setSurname(updatedSportsman.getSurname());
        sportsman.setPatronymic(updatedSportsman.getPatronymic());
        sportsman.setBirthDate(updatedSportsman.getBirthDate());

        Team team = updatedSportsman.getTeam();
//        if (updatedSportsman.getTeam().getId() == 0) {
        if (updatedSportsman.getTeam().getId() != null) {
            teamRepository.save(team);
        }
        sportsman.setTeam(updatedSportsman.getTeam());
        sportsman.setSportsTitle(updatedSportsman.getSportsTitle());
        sportsman.setPersonalCoach(updatedSportsman.getPersonalCoach());

        return sportsman;
    }


}






























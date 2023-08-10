package com.example.kursachrps.service;

import com.example.kursachrps.models.Application;
import com.example.kursachrps.models.Competition;
import com.example.kursachrps.models.Sportsman;
import com.example.kursachrps.models.Team;
import com.example.kursachrps.dto.SportsmanMainDTO;
import com.example.kursachrps.repositories.ApplicationRepository;
import com.example.kursachrps.repositories.CompetitionRepository;
import com.example.kursachrps.repositories.SportsmanMainRepository;
import com.example.kursachrps.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SportsmanService {

    private final CompetitionRepository competitionRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationService applicationService;
    private final SportsmanMainRepository sportsmanMainRepository;
    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SportsmanService(CompetitionRepository competitionRepository,
                            ApplicationRepository applicationRepository,
                            ApplicationService applicationService, SportsmanMainRepository sportsmanMainRepository, TeamRepository teamRepository, PasswordEncoder passwordEncoder) {
        this.competitionRepository = competitionRepository;
        this.applicationRepository = applicationRepository;
        this.applicationService = applicationService;
        this.sportsmanMainRepository = sportsmanMainRepository;
        this.teamRepository = teamRepository;
        this.passwordEncoder = passwordEncoder;
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
        if (updatedSportsman.getTeam().getId() == 0) {
            teamRepository.save(team);
        }
        sportsman.setTeam(updatedSportsman.getTeam());
        sportsman.setSportsTitle(updatedSportsman.getSportsTitle());
        sportsman.setPersonalCoach(updatedSportsman.getPersonalCoach());

        return sportsman;
    }


}






























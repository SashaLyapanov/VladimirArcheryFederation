package com.example.kursachrps.service;

import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.mapper.ApplicationMapper;
import com.example.kursachrps.models.*;
import com.example.kursachrps.dto.SportsmanMainDTO;
import com.example.kursachrps.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final SportsmanMainRepository sportsmanMainRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SportsmanService(CompetitionRepository competitionRepository,
                            ApplicationRepository applicationRepository,
                            ApplicationService applicationService,
                            ApplicationMapper applicationMapper,
                            SportsmanMainRepository sportsmanMainRepository,
                            PasswordEncoder passwordEncoder) {
        this.competitionRepository = competitionRepository;
        this.applicationRepository = applicationRepository;
        this.applicationService = applicationService;
        this.applicationMapper = applicationMapper;
        this.sportsmanMainRepository = sportsmanMainRepository;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * Метод для регистрации спортсмена на соревнования
     */
    @Transactional
    public void registrateSportsman(String sportsmanId, String competitionId, Application application) {

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
        sportsman.setSportsTitle(updatedSportsman.getSportsTitle());

        return sportsman;
    }

    /**
     * Метод для получения всех спортсменов по Соревнованию и Типу лука
     */
    @Transactional
    public List<SportsmanDTO> getAllSportmanByCompetitionAndBowType(String competitionId, String bowTypeName) {
        if (Objects.equals(bowTypeName, "all")) {
            return applicationService.getSportsmenFromApplications(applicationMapper.fromApplication(applicationService.getApplicationsForCompetition(competitionId)));
        } else {
            return applicationService.getSportsmenFromApplications(applicationMapper.fromApplication(applicationService.getApplicationsForCompetitionAndBowType(competitionId, bowTypeName)));
        }
    }


}






























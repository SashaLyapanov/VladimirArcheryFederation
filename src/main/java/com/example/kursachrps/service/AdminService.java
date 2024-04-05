package com.example.kursachrps.service;

import com.example.kursachrps.models.*;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    private final SportsmanMainRepository sportsmanMainRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMainRepository userMainRepository;
    private final CompetitionRepository competitionRepository;

    @Autowired
    public AdminService(SportsmanMainRepository sportsmanMainRepository,
                        PasswordEncoder passwordEncoder,
                        UserMainRepository userMainRepository,
                        CompetitionRepository competitionRepository) {
        this.sportsmanMainRepository = sportsmanMainRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMainRepository = userMainRepository;
        this.competitionRepository = competitionRepository;
    }


    /////////////////////////////////////////////////////////////////////////////////
                    //      Реализация CRUD спортсменов        //
    /////////////////////////////////////////////////////////////////////////////////


    @Transactional
    public Sportsman saveSportsman(Sportsman sportsman) {
        sportsman.setRole(Role.SPORTSMAN);
        sportsman.setStatus(Status.ACTIVE);

        return sportsmanMainRepository.save(sportsman);
    }

    //Метод, позволяющий захешировать пароль при создании спортсмена Администратором
    @Transactional
    public void hashPassword(SportsmanDTO sportsmanDTO) {
        sportsmanDTO.setPassword(passwordEncoder.encode(sportsmanDTO.getPassword()));
    }

    //Метод для получения спортсмена (Sportsman) по email
    public Sportsman getSportsmanByEmail(String email) {
        return sportsmanMainRepository.findByEmail(email).orElse(null);
    }

    //Метод для получения спортсмена (Sportsman) по email
    public Sportsman getSportsmanById(String id) {
        return sportsmanMainRepository.findById(id).orElse(null);
    }

    //Метод для вывода всех спортсменов (Sportsman) из БД
    public List<Sportsman> showAllSportsmen() {
        return sportsmanMainRepository.findAll(Sort.by("surname"));
    }

    //Метод для блокировки user'a по id.
    @Transactional
    public User blockingUser(String id) {
        User user = userMainRepository.findById(id).orElse(null);
        assert user != null;
        user.setStatus(Status.BANNED);
        return user;
    }

    //Метод для разблокировки user'a по id.
    @Transactional
    public User unblockingUser(String id) {
        User user = userMainRepository.findById(id).orElse(null);
        assert user != null;
        user.setStatus(Status.ACTIVE);
        return user;
    }


    @Transactional
    public void editSportsman(String id, Sportsman updatedSportsman) {

        Sportsman sportsman = sportsmanMainRepository.findById(id).orElse(null);

        assert sportsman != null;
        sportsman.setFirstName(updatedSportsman.getFirstName());
        sportsman.setSurname(updatedSportsman.getSurname());
        sportsman.setPatronymic(updatedSportsman.getPatronymic());
        sportsman.setBirthDate(updatedSportsman.getBirthDate());
        sportsman.setRegion(updatedSportsman.getRegion());
        sportsman.setSex(updatedSportsman.getSex());
        sportsman.setSportsTitle(updatedSportsman.getSportsTitle());
        sportsman.setIsRegionalTeamSportsman(updatedSportsman.getIsRegionalTeamSportsman());
    }


    /////////////////////////////////////////////////////////////////////////////////
                    //      Реализация CRUD соревнований        //
    /////////////////////////////////////////////////////////////////////////////////

    @Transactional
    public Competition createCompetition(Competition competition) {
        competition.setStatus(StatusOfCompetition.FUTURE);
        return competitionRepository.save(competition);
    }

    @Transactional
    public Competition editCompetition(String id, Competition updatedCompetition) {
        Competition competition = competitionRepository.findById(id).orElse(null);

        assert competition != null;
        competition.setName(updatedCompetition.getName());
        competition.setPlace(updatedCompetition.getPlace());
        competition.setType(updatedCompetition.getType());
        competition.setCategories(updatedCompetition.getCategories());
        competition.setBowTypeList(updatedCompetition.getBowTypeList());
        competition.setMainJudge(updatedCompetition.getMainJudge());
        competition.setSecretary(updatedCompetition.getSecretary());
        competition.setZamJudge(updatedCompetition.getZamJudge());
        competition.setJudges(updatedCompetition.getJudges());
        competition.setDate(updatedCompetition.getDate());
        competition.setStatus(StatusOfCompetition.FUTURE);
        return competition;
    }

    @Transactional
    public void changeStatusOfCompetition(String id) {
        Competition competition = competitionRepository.findById(id).orElse(null);
        assert competition != null;
        competition.setStatus(StatusOfCompetition.PRESENT);
    }

    @Transactional
    public void deleteCompetition(String id) {
        Competition competition = competitionRepository.findById(id).orElse(null);
        assert competition != null;
        competitionRepository.delete(competition);
    }
}

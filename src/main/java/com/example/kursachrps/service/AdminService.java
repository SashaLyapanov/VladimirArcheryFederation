package com.example.kursachrps.service;

import com.example.kursachrps.Models.*;
import com.example.kursachrps.dto.CoachDTO;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    private final SportsmanMainRepository sportsmanMainRepository;
    private final CoachMainRepository coachMainRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMainRepository userMainRepository;
    private final TeamRepository teamRepository;
    private final CompetitionRepository competitionRepository;

    @Autowired
    public AdminService(SportsmanMainRepository sportsmanMainRepository,
                        CoachMainRepository coachMainRepository,
                        PasswordEncoder passwordEncoder,
                        UserMainRepository userMainRepository,
                        TeamRepository teamRepository,
                        CompetitionRepository competitionRepository) {
        this.sportsmanMainRepository = sportsmanMainRepository;
        this.coachMainRepository = coachMainRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMainRepository = userMainRepository;
        this.teamRepository = teamRepository;
        this.competitionRepository = competitionRepository;
    }


    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////////Реализация CRUD спортсменов//////////////////////////////
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
    //Метод, позволяющий захешировать пароль при создании Тренера Администратором
    @Transactional
    public void hashPassword(CoachDTO coachDTO) {
        coachDTO.setPassword(passwordEncoder.encode(coachDTO.getPassword()));
    }



    //Метод для получения спортсмена (Sportsman) по email
    @Transactional
    public Sportsman getSportsmanByEmail(String email) {
        return sportsmanMainRepository.findByEmail(email).orElse(null);
    }

    //Метод для вывода всех спортсменов (Sportsman) из БД
    @Transactional
    public List<Sportsman> showAllSportsmen() { return sportsmanMainRepository.findAll(); }

    //Метод для блокировки user'a по email.
    @Transactional
    public User blockingUser(String email) {
        User user = userMainRepository.findByEmail(email).orElse(null);
        assert user != null;
        user.setStatus(Status.BANNED);
        return user;
    }
    //Метод для разблокировки user'a по email.
    @Transactional
    public User unblockingUser(String email) {
        User user = userMainRepository.findByEmail(email).orElse(null);
        assert user != null;
        user.setStatus(Status.ACTIVE);
        return user;
    }


    @Transactional
    public void editSportsman(String email, Sportsman updatedSportsman) {

        Sportsman sportsman = sportsmanMainRepository.findByEmail(email).orElse(null);

        assert sportsman != null;
        sportsman.setFirstName(updatedSportsman.getFirstName());
        sportsman.setSurname(updatedSportsman.getSurname());
        sportsman.setPatronymic(updatedSportsman.getPatronymic());
        sportsman.setBirthDate(updatedSportsman.getBirthDate());
        sportsman.setRegion(updatedSportsman.getRegion());
        sportsman.setSex(updatedSportsman.getSex());

        Team team = updatedSportsman.getTeam();
        if (updatedSportsman.getTeam().getId() == 0) {
            teamRepository.save(team);
        }
        sportsman.setTeam(updatedSportsman.getTeam());
        sportsman.setSportsTitle(updatedSportsman.getSportsTitle());
        sportsman.setPersonal_coach(updatedSportsman.getPersonal_coach());
    }


    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////////Реализация CRUD тренеров/////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    @Transactional
    public Coach saveCoach(Coach coach) {
        coach.setRole(Role.COACH);
        coach.setStatus(Status.ACTIVE);

        Team team = coach.getTeam();
        if (coach.getTeam().getId() == 0) {
            teamRepository.save(team);
        }

        return coachMainRepository.save(coach);
    }


    @Transactional
    public List<Coach> showAllCoaches() { return coachMainRepository.findAll(); }

    @Transactional
    public Coach getCoachByEmail(String email) { return coachMainRepository.findByEmail(email).orElse(null); }


    @Transactional
    public void editCoach(String email, Coach updatedCoach) {

        Coach coach = coachMainRepository.findByEmail(email).orElse(null);

        assert coach != null;
        coach.setFirstName(updatedCoach.getFirstName());
        coach.setSurname(updatedCoach.getSurname());
        coach.setPatronymic(updatedCoach.getPatronymic());
        coach.setBirthDate(updatedCoach.getBirthDate());
        coach.setRegion(updatedCoach.getRegion());
        coach.setSex(updatedCoach.getSex());
        coach.setSportsTitle(updatedCoach.getSportsTitle());

        Team team = updatedCoach.getTeam();
        if (updatedCoach.getTeam().getId() == 0) {
            teamRepository.save(team);
        }

        coach.setTeam(updatedCoach.getTeam());
        coach.setQualification(updatedCoach.getQualification());
        coach.setTeam(updatedCoach.getTeam());
        coach.setBowTypeList(updatedCoach.getBowTypeList());
    }


    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////////Реализация CRUD соревнований/////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    @Transactional
    public void createCompetition(Competition competition) {
        competition.setStatus(StatusOfCompetition.FUTURE);
        competitionRepository.save(competition);
    }

    @Transactional
    public void editCompetition(int id, Competition updatedCompetition) {
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
    }


    @Transactional
    public void changeStatusOfCompetition(int id) {
        Competition competition = competitionRepository.findById(id).orElse(null);
        assert competition != null;
        competition.setStatus(StatusOfCompetition.PRESENT);
    }
































}

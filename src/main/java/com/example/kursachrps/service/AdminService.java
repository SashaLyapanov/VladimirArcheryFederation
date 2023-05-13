package com.example.kursachrps.service;

import com.example.kursachrps.Models.*;
import com.example.kursachrps.dto.AdditionalDTO.SportsTitleDTO;
import com.example.kursachrps.dto.CoachDTO;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.mapper.UserMapper;
import com.example.kursachrps.repositories.*;
import com.example.kursachrps.repositories.RegistrAndAuth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AdminService {

    private final SportsmanRepository sportsmanRepository;
    private final SportsmanMainRepository sportsmanMainRepository;
    private final CoachRepository coachRepository;
    private final CoachMainRepository coachMainRepository;
    private final JudgeRepository judgeRepository;
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMainRepository userMainRepository;
    private final SportsTitleRepository sportsTitleRepository;
    private final UserMapper userMapper;
    private final TeamRepository teamRepository;

    @Autowired
    public AdminService(SportsmanRepository sportsmanRepository,
                        SportsmanMainRepository sportsmanMainRepository,
                        CoachRepository coachRepository,
                        CoachMainRepository coachMainRepository,
                        JudgeRepository judgeRepository,
                        AdminRepository adminRepository,
                        UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        UserMainRepository userMainRepository,
                        SportsTitleRepository sportsTitleRepository,
                        UserMapper userMapper,
                        TeamRepository teamRepository) {
        this.sportsmanRepository = sportsmanRepository;
        this.sportsmanMainRepository = sportsmanMainRepository;
        this.coachRepository = coachRepository;
        this.coachMainRepository = coachMainRepository;
        this.judgeRepository = judgeRepository;
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMainRepository = userMainRepository;
        this.sportsTitleRepository = sportsTitleRepository;
        this.userMapper = userMapper;
        this.teamRepository = teamRepository;
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
    public SportsmanDTO hashPassword(SportsmanDTO sportsmanDTO) {
        sportsmanDTO.setPassword(passwordEncoder.encode(sportsmanDTO.getPassword()));
        return sportsmanDTO;
    }
    //Метод, позволяющий захешировать пароль при создании Тренера Администратором
    public CoachDTO hashPassword(CoachDTO coachDTO) {
        coachDTO.setPassword(passwordEncoder.encode(coachDTO.getPassword()));
        return coachDTO;
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
        user.setStatus(Status.BANNED);
        return user;
    }
    //Метод для разблокировки user'a по email.
    @Transactional
    public User unblockingUser(String email) {
        User user = userMainRepository.findByEmail(email).orElse(null);
        user.setStatus(Status.ACTIVE);
        return user;
    }


    @Transactional
    public Sportsman editSportsman(String email, Sportsman updatedSportsman) {

        Sportsman sportsman = sportsmanMainRepository.findByEmail(email).orElse(null);

        sportsman.setFirstName(updatedSportsman.getFirstName());
        sportsman.setSurname(updatedSportsman.getSurname());
        sportsman.setPatronymic(updatedSportsman.getPatronymic());
        sportsman.setBirthDate(updatedSportsman.getBirthDate());
        sportsman.setTeam(updatedSportsman.getTeam());
        sportsman.setSportsTitle(updatedSportsman.getSportsTitle());
        sportsman.setPersonal_coach(updatedSportsman.getPersonal_coach());

        return sportsman;
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
    public Coach editCoach(String email, Coach updatedCoach) {

        Coach coach = coachMainRepository.findByEmail(email).orElse(null);

        coach.setFirstName(updatedCoach.getFirstName());
        coach.setSurname(updatedCoach.getSurname());
        coach.setPatronymic(updatedCoach.getPatronymic());
        coach.setBirthDate(updatedCoach.getBirthDate());

        Team team = updatedCoach.getTeam();
        if (updatedCoach.getTeam().getId() == 0) {
            teamRepository.save(team);
        }

        coach.setTeam(updatedCoach.getTeam());
        coach.setQualification(updatedCoach.getQualification());
        coach.setTeam(updatedCoach.getTeam());
        coach.setBowTypeList(updatedCoach.getBowTypeList());

        return coach;
    }

    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////////Тестовые методы//////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    //Метод для создания спортсмена в БД. (Подразумевается, что создаем User, у которого role = SPORTSMAN,
    // и при этом каскадно должен создаться sportsman, где sportsman.id = user.id
    @Transactional
    public User save(User user) {
        user.setRole(Role.SPORTSMAN);
        user.setStatus(Status.ACTIVE);
        return userRepository.save(user);
    }

    @Transactional
    public Optional<User> show(String email) {
        return userMainRepository.findByEmail(email);
    }


}

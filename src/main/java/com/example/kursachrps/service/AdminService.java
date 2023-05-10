package com.example.kursachrps.service;

import com.example.kursachrps.Models.*;
import com.example.kursachrps.dto.SportsmanDTO;
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

    @Autowired
    public AdminService(SportsmanRepository sportsmanRepository,
                        SportsmanMainRepository sportsmanMainRepository,
                        CoachRepository coachRepository,
                        CoachMainRepository coachMainRepository,
                        JudgeRepository judgeRepository,
                        AdminRepository adminRepository,
                        UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        UserMainRepository userMainRepository) {
        this.sportsmanRepository = sportsmanRepository;
        this.sportsmanMainRepository = sportsmanMainRepository;
        this.coachRepository = coachRepository;
        this.coachMainRepository = coachMainRepository;
        this.judgeRepository = judgeRepository;
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMainRepository = userMainRepository;
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


    //Метод для получения спортсмена (Sportsman) по email
    @Transactional
    public Sportsman getSportsman(String email) {
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



    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////////Реализация CRUD тренеров/////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    @Transactional
    public List<Coach> showAllCoaches() { return coachMainRepository.findAll(); }



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

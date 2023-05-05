package com.example.kursachrps.service;

import com.example.kursachrps.Models.Role;
import com.example.kursachrps.Models.Sportsman;
import com.example.kursachrps.Models.Status;
import com.example.kursachrps.Models.User;
import com.example.kursachrps.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AdminService {

    private final SportsmanRepository sportsmanRepository;
    private final CoachRepository coachRepository;
    private final JudgeRepository judgeRepository;
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    private final UserMainRepository userMainRepository;

    @Autowired
    public AdminService(SportsmanRepository sportsmanRepository,
                        CoachRepository coachRepository,
                        JudgeRepository judgeRepository,
                        AdminRepository adminRepository,
                        UserRepository userRepository,
                        UserMainRepository userMainRepository) {
        this.sportsmanRepository = sportsmanRepository;
        this.coachRepository = coachRepository;
        this.judgeRepository = judgeRepository;
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.userMainRepository = userMainRepository;
    }


    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////////Реализация CRUD спортсменов//////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////


    @Transactional
    public Sportsman saveSportsman(Sportsman sportsman) {
        sportsman.setRole(Role.SPORTSMAN);
        sportsman.setStatus(Status.ACTIVE);

        return sportsmanRepository.save(sportsman);
    }


    @Transactional
    public User getSportsman(String email) {
        return sportsmanRepository.findByEmail(email).orElseThrow();
    }


    public List<User> showAllSportsmen() {
        return sportsmanRepository.findAll();
    }


    public List<User> showAllUsers() {
        return userMainRepository.findAll();
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

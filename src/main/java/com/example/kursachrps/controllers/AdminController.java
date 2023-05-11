package com.example.kursachrps.controllers;

import com.example.kursachrps.Models.Coach;
import com.example.kursachrps.Models.Sportsman;
import com.example.kursachrps.Models.User;
import com.example.kursachrps.dto.Administratior.CoachAdmDTO;
import com.example.kursachrps.dto.Administratior.SportsmanAdmDTO;
import com.example.kursachrps.dto.CoachDTO;
import com.example.kursachrps.dto.CompetitionDTO;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.dto.UserDTO;
import com.example.kursachrps.mapper.CompetitionMapper;
import com.example.kursachrps.mapper.UserMapper;
import com.example.kursachrps.repositories.CompetitionRepository;
import com.example.kursachrps.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserMapper userMapper;
    private final CompetitionRepository competitionRepository;
    private final CompetitionMapper competitionMapper;

    @Autowired
    public AdminController(AdminService adminService, UserMapper userMapper, CompetitionRepository competitionRepository, CompetitionMapper competitionMapper) {
        this.adminService = adminService;
        this.userMapper = userMapper;
        this.competitionRepository = competitionRepository;
        this.competitionMapper = competitionMapper;
    }


    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////////Реализация CRUD спортсменов//////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////


    /**
     * Метод для вывода всех Sportsman'ов споском
     */
    @GetMapping("sportsmen")
    public List<SportsmanAdmDTO> getAllSportsmen() {

        List<Sportsman> sportsmen = new ArrayList<>();
        sportsmen = adminService.showAllSportsmen();
        List<SportsmanAdmDTO> dto = userMapper.fromSportsmanList(sportsmen);

        return dto;
    }

    /**
     * Метод для вывода спортсмена (Sportsman)
     */
//    @GetMapping("sportsman/{email}")
    @GetMapping("sportsman")
    public SportsmanAdmDTO getSportsman(@RequestParam String email) {
        SportsmanAdmDTO sportsmanAdmDTO = userMapper.fromSportsman(adminService.getSportsmanByEmail(email));

        return sportsmanAdmDTO;
    }


    /**
     * Метод для создания спортсмена в системе (регистрация от Админа)
     * JSON (email, password, firstName, surname, patronymic, birthDate)
     */
    @Transactional
    @PostMapping("createSportsman")
    public Sportsman createSportsman(@RequestBody @Valid SportsmanDTO sportsmanDTO) {

        adminService.hashPassword(sportsmanDTO);
        Sportsman sportsman = userMapper.fromSportsmanDTO(sportsmanDTO);
        return adminService.saveSportsman(sportsman);
    }


    @PutMapping("editSportsman")
    public SportsmanAdmDTO editSportsman(@RequestParam String email, @RequestBody @Valid SportsmanAdmDTO sportsmanAdmDTO) {

        Sportsman sportsman = userMapper.fromSportsmanAdmDTO(sportsmanAdmDTO);
        adminService.editSportsman(email, sportsman);

        return sportsmanAdmDTO;
    }



    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////////Реализация CRUD тренеров/////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////
////////!!!!!!!!!!!!!!!!!!У тренеров почему-то не выводится список типов лука, из которых они стреляют!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    /**
     * Метод для получения всех тренеров в системе
     */
    @GetMapping("coaches")
    public List<CoachAdmDTO> getCoaches() {

        List<Coach> coaches = adminService.showAllCoaches();

        List<CoachAdmDTO> coachesDTO = userMapper.fromCoachList(coaches);

        return coachesDTO;
    }

    /**
     * Метод для получения тренера по email
     */
    @GetMapping("coach")
    public CoachAdmDTO getCoach(@RequestParam String email) {

        Coach coach = adminService.getCoachByEmail(email);

        CoachAdmDTO coachesDTO = userMapper.fromCoach(coach);

        return coachesDTO;
    }

    /**
     * Метод для создания тренера в системе (регистрация от Админа)
     * JSON (email, password, firstName, surname, patronymic, birthDate)
     */
    @Transactional
    @PostMapping("createCoach")
    public Coach createCoach(@RequestBody @Valid CoachDTO coachDTO) {

        adminService.hashPassword(coachDTO);
        Coach coach = userMapper.fromCoachDTO(coachDTO);
        return adminService.saveCoach(coach);
    }


    @PutMapping("editCoach")
    public CoachAdmDTO editCoach(@RequestParam String email, @RequestBody @Valid CoachAdmDTO coachAdmDTO) {

        Coach coach = userMapper.fromcoachAdmDTO(coachAdmDTO);
        adminService.editCoach(email, coach);

        return coachAdmDTO;
    }


    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////////Универсальные методя для всех////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////



    @PutMapping("blockUser")
    public User blockingSportsman(@RequestParam @Valid String email) {

        User user = adminService.blockingUser(email);
        return user;
    }
    @PutMapping("unlockUser")
    public User unblockingSportsman(@RequestParam @Valid String email) {

        User user = adminService.unblockingUser(email);
        return user;
    }

    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////////Реализация CRUD соревнований/////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    /**
     *Метод для вывода всех соревнований
     */
    @GetMapping("competitions")
    public List<CompetitionDTO> getCompetitions() {
        return competitionMapper.fromCompetition(competitionRepository.findAll());
    }










    /////////////////////////////////////////////////////////////////////////////////
    //////////Тестовые методы для проверки работоспособности/////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    @PostMapping("create")
    public User createUserLikeSportsman(@RequestBody @Valid UserDTO userDTO) {
        User user = userMapper.convert(userDTO);
        user = adminService.save(user);

        return user;
    }

    @GetMapping("info/{email}")
    public UserDTO showInfo(@PathVariable String email) throws ChangeSetPersister.NotFoundException {
        User user = adminService.show(email).orElseThrow(ChangeSetPersister.NotFoundException::new);
        UserDTO userDTO = userMapper.transform(user);

        return userDTO;
    }
}

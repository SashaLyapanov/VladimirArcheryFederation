package com.example.kursachrps.controllers;

import com.example.kursachrps.models.*;
import com.example.kursachrps.dto.Administratior.CoachAdmDTO;
import com.example.kursachrps.dto.Administratior.SportsmanAdmDTO;
import com.example.kursachrps.dto.CoachDTO;
import com.example.kursachrps.dto.CompetitionCreateDTO;
import com.example.kursachrps.dto.NewDTO;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.mapper.CompetitionMapper;
import com.example.kursachrps.mapper.UserMapper;
import com.example.kursachrps.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserMapper userMapper;
    private final CompetitionMapper competitionMapper;

    @Autowired
    public AdminController(AdminService adminService, UserMapper userMapper, CompetitionMapper competitionMapper) {
        this.adminService = adminService;
        this.userMapper = userMapper;
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
//    @RequestMapping(value = "createSportsman", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
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
    public void blockingSportsman(@RequestParam @Valid String email) {

        User user = adminService.blockingUser(email);
    }
    @PutMapping("unlockUser")
    public void unblockingSportsman(@RequestParam @Valid String email) {

        User user = adminService.unblockingUser(email);
    }

    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////////Реализация CRUD соревнований/////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////




    /**
     * Метод для создания соревнований администратором
     */
    @PostMapping("createCompetition")
    public CompetitionCreateDTO createCompetition(@RequestBody CompetitionCreateDTO competitionCreateDTO) {

        Competition competition = competitionMapper.fromCompetitionCreateDTO(competitionCreateDTO);
        adminService.createCompetition(competition);

        return competitionCreateDTO;
    }

    /**
     * Метод редактирования соревнований
     */
    @PutMapping("editCompetition")
    public Competition editCompetition(@RequestParam int id, @RequestBody CompetitionCreateDTO updatedCompetition) {

        Competition competition = competitionMapper.fromCompetitionCreateDTO(updatedCompetition);
        adminService.editCompetition(id, competition);
        return competition;
    }


    /**
     * Метод для смены статуса соревнованиям
     */
    @PutMapping("changeStatusCompetition")
    public void changeStatusOfCompetition(@RequestParam int id) {
        adminService.changeStatusOfCompetition(id);
    }


    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////////Реализация CRUD новостей/////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////


    /**
     * Метод для шаблонного создания новости
     */
    @PostMapping("createNew")
    public void createNew(@RequestBody NewDTO newDTO) {

    }


    /**
     * Метод для шаблонного редактирования новости
     */
    @PutMapping("changeNew")
    public void changeNew(@RequestParam int newId, @RequestBody NewDTO newDTO) {

    }









}

package com.example.kursachrps.controllers;

import com.example.kursachrps.mapper.GeneralMapper;
import com.example.kursachrps.models.*;
import com.example.kursachrps.dto.Administratior.SportsmanAdmDTO;
import com.example.kursachrps.dto.CompetitionCreateDTO;
import com.example.kursachrps.dto.ArticleDTO;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.mapper.CompetitionMapper;
import com.example.kursachrps.mapper.UserMapper;
import com.example.kursachrps.service.AdminService;
import com.example.kursachrps.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserMapper userMapper;
    private final CompetitionMapper competitionMapper;
    private final ArticleService articleService;
    private final GeneralMapper generalMapper;

    @Autowired
    public AdminController(AdminService adminService, UserMapper userMapper, CompetitionMapper competitionMapper,
                           ArticleService articleService, GeneralMapper generalMapper) {
        this.adminService = adminService;
        this.userMapper = userMapper;
        this.competitionMapper = competitionMapper;
        this.articleService = articleService;
        this.generalMapper = generalMapper;
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
    ////////////////////////Универсальные методы для всех////////////////////////////
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
    public Competition editCompetition(@RequestParam String id, @RequestBody CompetitionCreateDTO updatedCompetition) {

        Competition competition = competitionMapper.fromCompetitionCreateDTO(updatedCompetition);
//        adminService.editCompetition(id, competition);
//        return competition;
        return adminService.editCompetition(id, competition);
    }


    /**
     * Метод для смены статуса соревнованиям
     */
    @PutMapping("changeStatusCompetition")
    public void changeStatusOfCompetition(@RequestParam String id) {
        adminService.changeStatusOfCompetition(id);
    }


    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////////Реализация CRUD новостей/////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////


    /**
     * Метод для шаблонного создания новости
     */
    @PostMapping("createArticle")
    public void createArticle(@RequestParam(name = "name") String name,
                              @RequestParam(name = "body") String body,
                              @RequestParam(name = "file1", required = false) MultipartFile file1) throws IOException {
        Article article = new Article();
        article.setName(name);
        article.setBody(body);
        articleService.saveArticle(article, file1);
    }

    @PostMapping("deleteArticle")
    public void deleteArticle(@RequestParam String articleId) {
        articleService.deleteArticle(articleId);
    }


    /**
     * Метод для шаблонного редактирования новости
     */
    @PutMapping("changeNew")
    public void changeNew(@RequestParam String newId, @RequestBody ArticleDTO articleDTO) {

    }
}

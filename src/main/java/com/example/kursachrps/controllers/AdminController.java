package com.example.kursachrps.controllers;

import com.example.kursachrps.models.*;
import com.example.kursachrps.dto.Administratior.SportsmanAdmDTO;
import com.example.kursachrps.dto.CompetitionCreateDTO;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.mapper.CompetitionMapper;
import com.example.kursachrps.mapper.UserMapper;
import com.example.kursachrps.service.AboutFederationService;
import com.example.kursachrps.service.AdminService;
import com.example.kursachrps.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserMapper userMapper;
    private final CompetitionMapper competitionMapper;
    private final ArticleService articleService;
    private final AboutFederationService aboutFederationService;

    @Autowired
    public AdminController(AdminService adminService, UserMapper userMapper, CompetitionMapper competitionMapper,
                           ArticleService articleService, AboutFederationService aboutFederationService) {
        this.adminService = adminService;
        this.userMapper = userMapper;
        this.competitionMapper = competitionMapper;
        this.articleService = articleService;
        this.aboutFederationService = aboutFederationService;
    }


    /////////////////////////////////////////////////////////////////////////////////
    //      Реализация CRUD спортсменов     //
    /////////////////////////////////////////////////////////////////////////////////

    /**
     * Метод для вывода всех Sportsman'ов споском (сортируются по алфавиту по фамилии)
     */
    @GetMapping("sportsmen")
    public List<SportsmanAdmDTO> getAllSportsmen() {
        List<Sportsman> sportsmen = adminService.showAllSportsmen();
        return userMapper.fromSportsmanList(sportsmen);
    }

    /**
     * Метод для вывода спортсмена по email(Sportsman)
     */
    @GetMapping("sportsmanByEmail")
    public SportsmanAdmDTO getSportsmanByEmail(@RequestParam String email) {
        return userMapper.fromSportsman(adminService.getSportsmanByEmail(email));
    }

    /**
     * Метод для вывода спортсмена по id
     */
    @GetMapping("sportsmanById")
    public SportsmanAdmDTO getSportsmanById(@RequestParam String id) {
        return userMapper.fromSportsman(adminService.getSportsmanById(id));
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
    public SportsmanAdmDTO editSportsman(@RequestParam String id, @RequestBody @Valid SportsmanAdmDTO sportsmanAdmDTO) {
        Sportsman sportsman = userMapper.fromSportsmanAdmDTO(sportsmanAdmDTO);
        adminService.editSportsman(id, sportsman);
        return sportsmanAdmDTO;
    }

    /////////////////////////////////////////////////////////////////////////////////
    //      Блокировка и разблокировка пользователей      //
    /////////////////////////////////////////////////////////////////////////////////

    @PutMapping("blockUser")
    public void blockingSportsman(@RequestParam @Valid String id) {
        adminService.blockingUser(id);
    }

    @PutMapping("unlockUser")
    public void unblockingSportsman(@RequestParam @Valid String id) {
        adminService.unblockingUser(id);
    }

    /////////////////////////////////////////////////////////////////////////////////
    //      Реалилзация CRUD соревнований      //
    /////////////////////////////////////////////////////////////////////////////////

    /**
     * Метод для создания соревнований администратором
     */
    @PostMapping("createCompetition")
    public Competition createCompetition(@RequestBody CompetitionCreateDTO competitionCreateDTO) {
        Competition competition = competitionMapper.fromCompetitionCreateDTO(competitionCreateDTO);
        Competition savedCompetition = adminService.createCompetition(competition);
        adminService.createProtocolForCompetition(savedCompetition);
        return savedCompetition;
    }

    /**
     * Метод редактирования соревнований
     */
    @PutMapping("editCompetition")
    public Competition editCompetition(@RequestParam String id, @RequestBody CompetitionCreateDTO updatedCompetition) {
        Competition competition = competitionMapper.fromCompetitionCreateDTO(updatedCompetition);
        return adminService.editCompetition(id, competition);
    }

    /**
     * Метод для смены статуса соревнованиям
     */
    //TODO
    // Сделать следующую логику
    // В параметры метода добавить параметр для передачи статуса, на который будем менять
    // и дальше в adminService.changeStatusOfCompetition(id) реализовать логику по смене статуса именно на указанынй в параметрах
    @PutMapping("changeStatusCompetition")
    public void changeStatusOfCompetition(@RequestParam String id) {
        adminService.changeStatusOfCompetition(id);
    }

    /**
     * Удаление неправильносозданных соревнований
     */
    @DeleteMapping("deleteCompetitionById")
    public void deleteCompetitionById(@RequestParam String id) {
        adminService.deleteCompetition(id);
    }

    /////////////////////////////////////////////////////////////////////////////////
    //      Реализация CRUD новостей        //
    /////////////////////////////////////////////////////////////////////////////////

    /**
     * Метод для шаблонного создания новости
     */
    @PostMapping("createArticle")
    public void createArticle(@RequestParam String name,
                              @RequestParam String body,
                              @RequestParam(required = false) MultipartFile file) throws IOException {
        Article article = new Article();
        article.setName(name);
        article.setBody(body);
        articleService.saveArticle(article, file);
    }

    /**
     * Метод для удаления новости по id
     */
    @PostMapping("deleteArticle")
    public void deleteArticleById(@RequestParam String articleId) {
        articleService.deleteArticle(articleId);
    }

    /**
     * Метод для шаблонного редактирования новости
     */
    @PutMapping("changeArticle")
    public void editArticle(@RequestParam String articleId,
                            @RequestParam String name,
                            @RequestParam String body,
                            @RequestParam(required = false) MultipartFile file) throws IOException {
        if (articleId != null) {
            articleService.editArticle(articleId, name, body, file);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    //      Реализация CRUD информации О Федерации        //
    /////////////////////////////////////////////////////////////////////////////////

    /**
     * Метод для изменения информации о федерации
     */
    //TODO
    // Реализовать статический сервис для работы с файлами
    @PutMapping("changeAboutFederation")
    public void changeAboutFederation(@RequestParam String aboutFederationId, @RequestParam(name = "managers") String managers,
                                      @RequestParam(name = "contacts") String contacts,
                                      @RequestParam(name = "file1", required = false) MultipartFile file1,
                                      @RequestParam(name = "file2", required = false) MultipartFile file2) throws IOException {
        AboutFederation aboutFederation = new AboutFederation();
        aboutFederation.setManagers(managers);
        aboutFederation.setContacts(contacts);
        aboutFederationService.editAboutFederation(aboutFederationId, aboutFederation, file1, file2);
    }
}

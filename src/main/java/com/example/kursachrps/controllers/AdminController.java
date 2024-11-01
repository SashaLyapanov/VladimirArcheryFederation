package com.example.kursachrps.controllers;

import com.example.kursachrps.dto.ArticleDTO;
import com.example.kursachrps.mapper.GeneralMapper;
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
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserMapper userMapper;
    private final CompetitionMapper competitionMapper;
    private final ArticleService articleService;
    private final GeneralMapper generalMapper;
    private final AboutFederationService aboutFederationService;

    @Autowired
    public AdminController(AdminService adminService, UserMapper userMapper, CompetitionMapper competitionMapper,
                           ArticleService articleService, GeneralMapper generalMapper, AboutFederationService aboutFederationService) {
        this.adminService = adminService;
        this.userMapper = userMapper;
        this.competitionMapper = competitionMapper;
        this.articleService = articleService;
        this.generalMapper = generalMapper;
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
    public CompetitionCreateDTO editCompetition(@RequestParam String id, @RequestBody CompetitionCreateDTO updatedCompetition) {
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
    @PostMapping(value = "createArticle")
    public ArticleDTO createArticle(@RequestParam String name,
                                    @RequestParam String body,
                                    @RequestParam(required = false) MultipartFile file) {
        Article article = new Article();
        article.setName(name);
        article.setBody(body);
        article = articleService.saveArticle(article, file);
        return generalMapper.fromArticle(article);
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
//    @PutMapping("changeArticle")
//    public void editArticle(@RequestParam String articleId,
//                            @RequestParam String name,
//                            @RequestParam String body,
//                            @RequestParam(required = false) MultipartFile file) throws IOException {
//        if (articleId != null) {
//            articleService.editArticle(articleId, name, body, file);
//        }
//    }
    @PutMapping("changeArticle")
    public void editArticle(@RequestBody Article article) {
        System.out.println("Редактируем новость");
        if (article != null) {
            articleService.editArticle(article.getId(), article.getName(), article.getBody(), null);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    //      Реализация CRUD информации О Федерации        //
    /////////////////////////////////////////////////////////////////////////////////

    /**
     * Метод для изменения информации о федерации
     */
    @PutMapping("changeAboutFederation")
    public ResponseEntity<?> changeAboutFederation(@RequestBody AboutFederation aboutFederationDTO) {
        AboutFederation aboutFederation = new AboutFederation();
        aboutFederation.setManagers(aboutFederationDTO.getManagers());
        aboutFederation.setContacts(aboutFederationDTO.getContacts());
        aboutFederation.setListLinks(aboutFederationDTO.getListLinks());
        boolean status = aboutFederationService.editAboutFederation(aboutFederationDTO.getId(), aboutFederation);
        if (status) {
            return ResponseEntity.ok(aboutFederation);
        } else {
            return ResponseEntity.badRequest().body("Что-то пошло не так при обновлении данных");
        }
    }


}

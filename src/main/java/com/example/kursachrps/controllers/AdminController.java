package com.example.kursachrps.controllers;

import com.example.kursachrps.dto.ArticleDTO;
import com.example.kursachrps.mapper.*;
import com.example.kursachrps.models.*;
import com.example.kursachrps.dto.Administratior.SportsmanAdmDTO;
import com.example.kursachrps.dto.CompetitionCreateDTO;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.service.AboutFederationService;
import com.example.kursachrps.service.ActivityFederationService;
import com.example.kursachrps.service.AdminService;
import com.example.kursachrps.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final SportsmanMapper sportsmanMapper;
    private final ActivityFederationService activityFederationService;

    @Autowired
    public AdminController(AdminService adminService, UserMapper userMapper, CompetitionMapper competitionMapper,
                           ArticleService articleService, GeneralMapper generalMapper, AboutFederationService aboutFederationService, SportsmanMapper sportsmanMapper, ActivityFederationService activityFederationService) {
        this.adminService = adminService;
        this.userMapper = userMapper;
        this.competitionMapper = competitionMapper;
        this.articleService = articleService;
        this.generalMapper = generalMapper;
        this.aboutFederationService = aboutFederationService;
        this.sportsmanMapper = sportsmanMapper;
        this.activityFederationService = activityFederationService;
    }


    /////////////////////////////////////////////////////////////////////////////////
    //      Реализация CRUD спортсменов     //
    /////////////////////////////////////////////////////////////////////////////////

    /**
     * Метод для вывода всех Sportsman'ов споском (сортируются по алфавиту по фамилии)
     */
    @GetMapping("sportsmen")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<SportsmanAdmDTO> getAllSportsmen() {
        List<Sportsman> sportsmen = adminService.showAllSportsmen();
        return userMapper.fromSportsmanList(sportsmen);
    }

    /**
     * Метод для вывода спортсмена по email(Sportsman)
     */
    @GetMapping("sportsmanByEmail")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public SportsmanAdmDTO getSportsmanByEmail(@RequestParam String email) {
        return userMapper.fromSportsman(adminService.getSportsmanByEmail(email));
    }

    /**
     * Метод для вывода спортсмена по id
     */
    @GetMapping("sportsmanById")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public SportsmanAdmDTO getSportsmanById(@RequestParam String id) {
        return userMapper.fromSportsman(adminService.getSportsmanById(id));
    }

    @GetMapping("sportsmenByFIO")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getSportsmenByFIO(@RequestParam(required = false) String surname,
                                               @RequestParam(required = false) String name,
                                               @RequestParam(required = false) String patronymic) {
        List<SportsmanAdmDTO> result = userMapper.fromSportsmanList(adminService.getSportsmanByFio(surname, name, patronymic));
        return ResponseEntity.ok(result);
    }

    /**
     * Метод для создания спортсмена в системе (регистрация от Админа)
     * JSON (email, password, firstName, surname, patronymic, birthDate)
     */
    @Transactional
    @PostMapping("createSportsman")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Sportsman createSportsman(@RequestBody @Valid SportsmanDTO sportsmanDTO) {
        adminService.hashPassword(sportsmanDTO);
        Sportsman sportsman = userMapper.fromSportsmanDTO(sportsmanDTO);
        return adminService.saveSportsman(sportsman);
    }

    @PutMapping("editSportsman")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public SportsmanAdmDTO editSportsman(@RequestParam String id, @RequestBody @Valid SportsmanAdmDTO sportsmanAdmDTO) {
        Sportsman sportsman = userMapper.fromSportsmanAdmDTO(sportsmanAdmDTO);
        adminService.editSportsman(id, sportsman);
        return sportsmanAdmDTO;
    }

    @PutMapping("addInRegionalTeam")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addInRegionalTeam(@RequestParam String id) {
        if (id.isEmpty()) {
            return ResponseEntity.badRequest().body("Спортсмен не найден в базе данных");
        }
        SportsmanDTO response = sportsmanMapper.fromSportsman(adminService.addSportsmanToRegionalTeam(id));
        if (response != null) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.badRequest().body("Спортсмен не найден в базе данных");
        }
    }

    @PutMapping("deleteFromRegionalTeam")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteFromRegionalTeam(@RequestParam String id) {
        if (id.isEmpty()) {
            return ResponseEntity.badRequest().body("Спортсмен не найден в базе данных");
        }
        SportsmanDTO response = sportsmanMapper.fromSportsman(adminService.deleteFromRegionalTeam(id));
        if (response != null) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.badRequest().body("Спортсмен не найден в базе данных");
        }
    }


    /////////////////////////////////////////////////////////////////////////////////
    //      Блокировка и разблокировка пользователей      //
    /////////////////////////////////////////////////////////////////////////////////

    @PutMapping("blockUser")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void blockingSportsman(@RequestParam @Valid String id) {
        adminService.blockingUser(id);
    }

    @PutMapping("unlockUser")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CompetitionCreateDTO editCompetition(@RequestParam String id, @RequestBody CompetitionCreateDTO updatedCompetition) {
        Competition competition = competitionMapper.fromCompetitionCreateDTO(updatedCompetition);
        return adminService.editCompetition(id, competition);
    }

    /**
     * Удаление неправильносозданных соревнований
     */
    @DeleteMapping("deleteCompetitionById")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteCompetitionById(@RequestParam String id) {
        adminService.deleteCompetition(id);
    }

    @PostMapping("addFilesToCompetition")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addFilesToCompetition(@RequestParam String competitionId, @RequestParam(name = "files", required = false) MultipartFile[] files) {
        boolean response = adminService.addFilesToCompetition(competitionId, files);
        if (response) {
            return ResponseEntity.ok().body("Файлы успешно добавлены");
        } else {
            return ResponseEntity.badRequest().body("Не удалось добавить файлы в систему");
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    //      Реализация CRUD новостей        //
    /////////////////////////////////////////////////////////////////////////////////

    /**
     * Метод для шаблонного создания новости
     */
    @PostMapping(value = "createArticle")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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

    /**
     * Метод для изменения информации о федерации
     */
    @PostMapping(value = "changeFilesAboutFederation", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> changeFilesAboutFederation(@RequestParam(name = "files", required = false) MultipartFile[] files) {
        boolean response = aboutFederationService.editFilesAboutFederation("7fa1257a-332b-258d-bca6-ba78fa263e0f", files);

        if (response) {
            return ResponseEntity.ok().body("Файлы успешно обновлены");
        } else {
            return ResponseEntity.badRequest().body("Не удалось обновить файлы");
        }
    }

    @PostMapping(value = "changeFilesActivityFederation", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> changeFilesActivityFederation(@RequestParam String flag, @RequestParam(name = "files", required = false) MultipartFile[] files) {
        boolean response = activityFederationService.editFilesActivityFederation("7fa1257a-152j-258d-bca6-ba78fa263e0f", flag, files);
        if (response) {
            return ResponseEntity.ok().body("Файлы успешно обновлены");
        } else {
            return ResponseEntity.badRequest().body("Не удалось обновить файлы");
        }
    }

//    @PostMapping(value = "changeFilesActivityFederation3D", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public ResponseEntity<?> changeFilesActivityFederation3D(@RequestParam(name = "files", required = false) MultipartFile[] files) {
//
//
//        return ResponseEntity.ok().body("Файлы успешно обновлены");
//    }
//
//    @PostMapping(value = "changeFilesActivityFederationClassic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public ResponseEntity<?> changeFilesActivityFederationClassic(@RequestParam(name = "files", required = false) MultipartFile[] files) {
//
//
//        return ResponseEntity.ok().body("Файлы успешно обновлены");
//    }
//
//    @PostMapping(value = "changeFilesActivityFederationBiathlon", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public ResponseEntity<?> changeFilesActivityFederationBiathlon(@RequestParam(name = "files", required = false) MultipartFile[] files) {
//
//
//        return ResponseEntity.ok().body("Файлы успешно обновлены");
//    }


    @PostMapping("addFilesToRegionalFederation")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addFilesToRegionalFederation(@RequestParam(name = "files", required = false) MultipartFile[] files) {
        boolean response = adminService.addFilesToRegionalFederation("c99ccd51-5731-42a3-9cfc-2ab07e3e9b4c", files);
        if (response) {
            return ResponseEntity.ok().body("Файлы успешно добавлены");
        } else {
            return ResponseEntity.badRequest().body("Не удалось добавить файлы в систему");
        }
    }

}

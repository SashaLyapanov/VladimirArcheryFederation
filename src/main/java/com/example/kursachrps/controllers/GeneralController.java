package com.example.kursachrps.controllers;

import com.example.kursachrps.dto.*;
import com.example.kursachrps.dto.AdditionalDTO.*;
import com.example.kursachrps.mapper.ApplicationMapper;
import com.example.kursachrps.mapper.CompetitionMapper;
import com.example.kursachrps.mapper.GeneralMapper;
import com.example.kursachrps.models.AboutFederation;
import com.example.kursachrps.models.Article;
import com.example.kursachrps.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/general")
public class GeneralController {

    private final ActivityFederationService activityFederationService;
    private final CompetitionMapper competitionMapper;
    private final GeneralService generalService;
    private final ApplicationService applicationService;
    private final ApplicationMapper applicationMapper;
    private final GeneralMapper generalMapper;
    private final ArticleService articleService;
    private final AboutFederationService aboutFederationService;
    private final RegionalTeamService regionalTeamService;

    public GeneralController(ActivityFederationService activityFederationService, CompetitionMapper competitionMapper,
                             GeneralService generalService, ApplicationService applicationService,
                             ApplicationMapper applicationMapper, GeneralMapper generalMapper, ArticleService articleService,
                             AboutFederationService aboutFederationService, RegionalTeamService regionalTeamService) {
        this.activityFederationService = activityFederationService;
        this.competitionMapper = competitionMapper;
        this.generalService = generalService;
        this.applicationService = applicationService;
        this.applicationMapper = applicationMapper;
        this.generalMapper = generalMapper;
        this.articleService = articleService;
        this.aboutFederationService = aboutFederationService;
        this.regionalTeamService = regionalTeamService;
    }

    @Value("${pdffiles}")
    private String pdfFilesPath;

    /**
     * Метод для просмотра всех заявок на определенные соревнования
     */
    @GetMapping("/applicationsForCompetition")
    List<ApplicationDTO> getApplications(@RequestParam String competitionId) {
        return applicationMapper.fromApplication(applicationService.getApplicationsForCompetition(competitionId));
    }

    /**
     * Метод для просмотра всех заявившихся спорстменов на определенные соревнования
     */
    @GetMapping("/declaredSportsmenForCompetition")
    List<SportsmanDTO> getDeclaredSportsmen(@RequestParam String competitionId) {
        return applicationService.getSportsmenDTOFromApplications(getApplications(competitionId));
    }

    /**
     * Запрос на получение всех регионов
     */
    @GetMapping("allRegions")
    List<RegionDTO> getRegions() {
        return generalMapper.fromRegion(generalService.getAllRegions());
    }

    /**
     * Запрос на получение всех спортивных титулов
     */
    @GetMapping("allSportsTitle")
    List<SportsTitleDTO> getAllSportsTitle() {
        return generalMapper.fromSportsTitle(generalService.getAllSportsTitle());
    }

    /**
     * Запрос на получение всех типов луков
     */
    @GetMapping("allBowTypes")
    List<BowTypeDTO> getAllBowType() {
        return generalMapper.fromBowType(generalService.getAllBowType());
    }

    /**
     * Запрос на получение всех категорий соревнований
     */
    @GetMapping("allCategories")
    List<CategoryDTO> getAllCategories() {
        return generalMapper.fromCategory(generalService.getAllCategory());
    }

    /**
     * Запрос на получение всех гендеров
     */
    @GetMapping("allSex")
    List<SexDTO> getAllSex() {
        return generalMapper.fromSex(generalService.getAllSex());
    }

    /**
     * Запрос на получение всех видов соревнований (3D, Target)
     */
    @GetMapping("allCompetitionTypes")
    List<CompetitionTypeDTO> getAllCompetitionTypes() {
        return generalMapper.fromCompetitionType(generalService.getAllCompetitionTypes());
    }

    /**
     * Запрос на получение всех типов лука при регистраци на определенные соревновния.
     */
    @GetMapping("allBowTypesByCompetition")
    List<BowTypeDTO> getAllBowTypeByCompetitionId(@RequestParam String competitionId) {
        return generalMapper.fromBowType(generalService.getAllBowTypeByCompetitionId(competitionId));
    }

    /**
     * Метод для скачивания pdf протокола
     */
    @GetMapping("/savePDFProtocol")
    public ResponseEntity<Resource> savePDFProtocol(@RequestParam String competitionId) throws IOException {
        String fileName = generalService.getProtocolNameByCompetitionId(competitionId);
//        File file = new File("C:\\Users\\-\\IdeaProjects\\KursachRPS\\src\\filePDF\\" + fileName);
        File file = new File(pdfFilesPath + fileName);
        //Реализация скачивания файла

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    /**
     * Метод для полечения всех новостей
     */
    @GetMapping("/getArticles")
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        List<Article> articleList = articleService.getAllArticles();
        for (Article article : articleList) {
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setId(article.getId());
            articleDTO.setName(article.getName());
            articleDTO.setBody(article.getBody());
            articleDTO.setDateTime(article.getDateTime());
            articleDTO.setLink(article.getLink());
            articleDTOList.add(articleDTO);
        }
        return ResponseEntity.ok(articleDTOList);
    }

    /**
     * Метод для отображения определенной новости (страница этой новости)
     */
    @GetMapping("getArticle")
    public ResponseEntity<?> getArticle(@RequestParam String articleId) {
        ArticleDTO articleDTO = generalMapper.fromArticle(articleService.getArticleById(articleId));
        if (articleDTO != null) {
            return ResponseEntity.ok(articleDTO);
        }
        else return ResponseEntity.badRequest().body("Не удалось получить запись новости с id" + articleId);
    }

    List<String> mapStringToList(String str) {
        if (str != null) {
            String[] items = str.split(", ");
            return Arrays.asList(items);
        } else {
            return null;
        }
    }


    /**
     * Метод для информации о федерации
     */
    @GetMapping("/getAboutFederation")
    public ResponseEntity<?> getAllAboutFederation() {
        AboutFederationDTO aboutFederationDTO;
        AboutFederation aboutFederation = aboutFederationService.getAllAboutFederation();
        aboutFederationDTO = generalMapper.fromAboutFederation(aboutFederation);
        if (aboutFederation.getListFileNames() != null) {
            aboutFederationDTO.setFileNames(mapStringToList(aboutFederation.getListFileNames()));
        }
        if (aboutFederation.getListLinks() != null) {
            aboutFederationDTO.setLinks(mapStringToList(aboutFederation.getListLinks()));
        }
        if (aboutFederationDTO != null) {
            return ResponseEntity.ok(aboutFederationDTO);
        } else {
            return ResponseEntity.badRequest().body("Не удалось извлечь информацию о федерации.");
        }
    }

    //////////////////////////////////////////
    //    СОРЕВНОВАНИЯ    //
    //////////////////////////////////////////

    /**
     * Метод для вывода всех соревнований, у которых статус PAST
     */
    @GetMapping("/allPastCompetitions")
    public List<CompetitionDTO> getAllPastCompetition() {
        return competitionMapper.fromCompetition(generalService.getPastCompetitions());
    }

    /**
     * Метод для вывода всех соревнований
     */
    @GetMapping("competitions")
    public List<CompetitionDTO> getCompetitions() {
        return competitionMapper.fromCompetition(generalService.showAllCompetitions());
    }

    /**
     * Метод для вывода всех соревнований со статусом Future or Present
     */
    @GetMapping("availableCompetitions")
    public List<CompetitionDTO> getAvailableCompetition() {
        return competitionMapper.fromCompetition(generalService.showAllAvailableCompetitions());
    }

    /**
     * Метод для вывода всех соревнований со статусом Future or Present
     */
    @GetMapping("pastCompetitions")
    public List<CompetitionDTO> getPastCompetition() {
        return competitionMapper.fromCompetition(generalService.showAllPastCompetitions());
    }

    @GetMapping("competition")
    public CompetitionDTO getCompetition(@RequestParam String id) {
        return competitionMapper.fromCompetition(generalService.showCompetitionById(id));
    }

    /**
     * Метод для вывода соревнования по дате
     */
    @GetMapping("competitionByDate")
    public List<CompetitionDTO> getCompetitions(@RequestParam Date date) {
        return competitionMapper.fromCompetition(generalService.showCompetitionByDate(date));
    }

    /**
     * Метод для вывода соревнования по названию
     */
    @GetMapping("competitionByName")
    public CompetitionDTO getCompetitionByName(@RequestParam String name) {
        return competitionMapper.fromCompetition(generalService.showCompetitionByName(name));
    }

    /**
     * Метод для получения соревнований по расширенному списку параметров
     */
    @GetMapping("competitionsByParams")
    public List<CompetitionDTO> getCompetitions(@RequestParam(required = false) String name,
                                                @RequestParam(required = false) String date,
                                                @RequestParam(required = false) String type){
        if (date == null || date.isEmpty()) {
            return (generalService.getCompetitionsBySearchParams(name, null, type));
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date localDate = formatter.parse(date);
                return (generalService.getCompetitionsBySearchParams(name, localDate, type));
            } catch (ParseException e) {
                System.out.println("Ошибка в парсинге даты: " + e);
            }
            return null;
        }
    }


    //////////////////////////////////////////
    //      СБОРНАЯ КОМАНДА     //
    //////////////////////////////////////////

    /**
     * Метод для получения спортсменов, входящих в состав сборной области
     */
    @GetMapping("regionalTeam")
    public List<SportsmanDTO> getRegionalTeam() {
        return regionalTeamService.getAllSportsman();
    }


    /**
     * метод для получения списка названий файлов на странице Сборная
     */
    @GetMapping("regionalTeamFiles")
    public List<String> getRegionalTeamFiles() { return regionalTeamService.getAllRegionalTeamFiles(); }

    //////////////////////////////////////////
    //      Деятельность федерации     //
    //////////////////////////////////////////

    /**
     * Метод для получения списка названий файлов на странице Деятельность федерации
     */
    @GetMapping("activityFederation")
    public List<String> getActivityFiles() { return activityFederationService.getAllRegionalActivityFiles(); }

}

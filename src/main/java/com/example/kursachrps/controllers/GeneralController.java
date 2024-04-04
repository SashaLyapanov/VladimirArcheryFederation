package com.example.kursachrps.controllers;

import com.example.kursachrps.dto.*;
import com.example.kursachrps.dto.AdditionalDTO.*;
import com.example.kursachrps.mapper.ApplicationMapper;
import com.example.kursachrps.mapper.CompetitionMapper;
import com.example.kursachrps.mapper.GeneralMapper;
import com.example.kursachrps.models.AboutFederation;
import com.example.kursachrps.models.Article;
import com.example.kursachrps.service.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/general")
public class GeneralController {

    private final CompetitionMapper competitionMapper;
    private final GeneralService generalService;
    private final ApplicationService applicationService;
    private final ApplicationMapper applicationMapper;
    private final GeneralMapper generalMapper;
    private final ArticleService articleService;
    private final AboutFederationService aboutFederationService;
    private final RegionalTeamService regionalTeamService;

    public GeneralController(CompetitionMapper competitionMapper, GeneralService generalService, ApplicationService applicationService,
                             ApplicationMapper applicationMapper, GeneralMapper generalMapper, ArticleService articleService,
                             AboutFederationService aboutFederationService, RegionalTeamService regionalTeamService) {
        this.competitionMapper = competitionMapper;
        this.generalService = generalService;
        this.applicationService = applicationService;
        this.applicationMapper = applicationMapper;
        this.generalMapper = generalMapper;
        this.articleService = articleService;
        this.aboutFederationService = aboutFederationService;
        this.regionalTeamService = regionalTeamService;
    }

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
        return applicationService.getSportsmenFromApplications(getApplications(competitionId));
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
    public ResponseEntity<Resource> savePDFProtocol(@RequestParam int competitionId) throws IOException {
        String fileName = generalService.getProtocolNameByCompetitionId(competitionId);
        File file = new File("C:\\Users\\-\\IdeaProjects\\KursachRPS\\src\\filePDF\\" + fileName);
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
    public ResponseEntity<List<ArticleDTO>> getAllArticles() throws IOException {
        List<ArticleDTO> articleDTOList = new ArrayList<ArticleDTO>();
        List<Article> articleList = articleService.getAllArticles();
        for (Article article : articleList) {
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setId(article.getId());
            articleDTO.setName(article.getName());
            articleDTO.setBody(article.getBody());
            articleDTO.setDateTime(article.getDateTime());
            articleDTO.setLink(article.getLink());
            articleDTO.setFileName(article.getFile().getOriginalFilename());
            articleDTO.setFileData(Arrays.toString(article.getFile().getBytes()));
            articleDTOList.add(articleDTO);
        }
        return ResponseEntity.ok(articleDTOList);
    }

    /**
     * Метод для отображения определенной новости (страница этой новости)
     */
    @GetMapping("getArticle")
    public ArticleDTO getArticle(@RequestParam String articleId) {
        return generalMapper.fromArticle(articleService.getArticleById(articleId));
    }


    /**
     * Метод для информации о федерации
     */
    @GetMapping("/getAboutFederation")
    public ResponseEntity<List<AboutFederationDTO>> getAllAboutFederation() throws IOException {
        List<AboutFederationDTO> aboutFederationDTOList = new ArrayList<AboutFederationDTO>();
        List<AboutFederation> aboutFederationList = aboutFederationService.getAllAboutFederation();
        for (AboutFederation aboutFederation : aboutFederationList) {
            AboutFederationDTO aboutFederationDTO = new AboutFederationDTO();
            aboutFederationDTO.setId(aboutFederation.getId());
            aboutFederationDTO.setManagers(aboutFederation.getManagers());
            aboutFederationDTO.setContacts(aboutFederation.getContacts());
            ;
            aboutFederationDTO.setLinkForRegulation(aboutFederation.getLinkForRegulation());
            aboutFederationDTO.setLinkForHistory(aboutFederation.getLinkForHistory());
            aboutFederationDTO.setFileRegulationName(aboutFederation.getRegulation().getOriginalFilename());
            aboutFederationDTO.setFileHistoryName(aboutFederation.getHistory().getOriginalFilename());
            aboutFederationDTO.setFileRegulationData(Arrays.toString(aboutFederation.getRegulation().getBytes()));
            aboutFederationDTO.setFileHistoryData(Arrays.toString(aboutFederation.getHistory().getBytes()));
            aboutFederationDTOList.add(aboutFederationDTO);
        }
        return ResponseEntity.ok(aboutFederationDTOList);
    }


    //////////////////////////////////////////
            //    СОРЕВНОВАНИЯ    //
    //////////////////////////////////////////
    /**
     * Метод для вывода всех соревнований, у которых статус PAST
     */
    @GetMapping("/allPastCompetitions")
    public List<CompetitionDTO> getAllPastCompetition() {
        return competitionMapper.fromCompetition(generalService.getPresentCompetitions());
    }


    /**
     * Метод для вывода всех соревнований
     */
    @GetMapping("competitions")
    public List<CompetitionDTO> getCompetitions() {
        return competitionMapper.fromCompetition(generalService.showAllCompetitions());
    }

    /**
     * Метод для вывода соревнования по дате
     */
    @GetMapping("competition")
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
     * Метод для поиска соревнований по названию, дате и категории спортсмена
     */
    @GetMapping("competitionNDC")
    public List<CompetitionDTO> getCompetitions(@RequestParam(required = false) String name,
                                                @RequestParam(required = false) Date date,
                                                @RequestParam(required = false) String categoryName) {
        return competitionMapper.fromCompetition(generalService.showCompetitionByNameDateCategory(name, date, categoryName));
    }

    /**
     * Метод для получения соревнований по расширенному списку параметров
     */
    @GetMapping("competitionsByParams")
    public List<CompetitionDTO> getCompetitions(@RequestParam(required = false) String name,
                                                @RequestParam(required = false) String place,
                                                @RequestParam(required = false) String type,
                                                @RequestParam(required = false, defaultValue = "0") int page,
                                                @RequestParam(required = false, defaultValue = "10") int size) {
//        Competition searchPattern = new Competition();
//        if (name != null && !name.isEmpty()) {
//            searchPattern.setName(name);
//        }
//        if (place != null && !place.isEmpty()) {
//            searchPattern.setPlace(place);
//        }
//        if (type != null && !type.isEmpty()) {
//            searchPattern.getType().setName(type);
//        }

        List<CompetitionDTO> competitions = generalService.getCompetitionsBySearchParams(name, place, type, PageRequest.of(page, size));

        return competitions;
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



}

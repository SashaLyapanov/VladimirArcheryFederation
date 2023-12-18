package com.example.kursachrps.controllers;

import com.example.kursachrps.dto.AdditionalDTO.*;
import com.example.kursachrps.dto.ApplicationDTO;
import com.example.kursachrps.dto.ArticleDTO;
import com.example.kursachrps.dto.CompetitionDTO;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.mapper.ApplicationMapper;
import com.example.kursachrps.mapper.CompetitionMapper;
import com.example.kursachrps.mapper.GeneralMapper;
import com.example.kursachrps.models.Article;
import com.example.kursachrps.service.ApplicationService;
import com.example.kursachrps.service.ArticleService;
import com.example.kursachrps.service.GeneralService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    public GeneralController (CompetitionMapper competitionMapper, GeneralService generalService, ApplicationService applicationService,
                              ApplicationMapper applicationMapper, GeneralMapper generalMapper, ArticleService articleService) {
        this.competitionMapper = competitionMapper;
        this.generalService = generalService;
        this.applicationService = applicationService;
        this.applicationMapper = applicationMapper;
        this.generalMapper = generalMapper;
        this.articleService = articleService;
    }

    /**
     *Метод для вывода всех соревнований
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
     * Метод для вывода соревнования по дате
     */
    @GetMapping("competitionByName")
    public CompetitionDTO getCompetitionByName(@RequestParam String name) {
        return competitionMapper.fromCompetition(generalService.showCompetitionByName(name));
    }

    /**
     * Метод для поиска соревнований по названию, дате и категории спортсмена
     */
    @GetMapping("competitionNDC")
    public List<CompetitionDTO> getCompetitions(@RequestParam (required = false) String name, @RequestParam (required = false) Date date, @RequestParam (required = false) String categoryName) {
        return competitionMapper.fromCompetition(generalService.showCompetitionByNameDateCategory(name, date, categoryName));
    }

    /**
     * Метод для просмотра всех заявок на определенные соревнования
     */
    @GetMapping("/applicationsForCompetition")
    List<ApplicationDTO> getApplications(@RequestParam String competitionId) {

        List<ApplicationDTO> applicationDTOList = applicationMapper.fromApplication(applicationService.getApplicationsForCompetition(competitionId));
        return applicationDTOList;
    }

    /**
     * Метод для просмотра всех заявившихся спорстменов на определенные соревнования
     */
    @GetMapping("/declaredSportsmenForCompetition")
    List<SportsmanDTO> getDeclaredSportsmen(@RequestParam String competitionId) {
        List<ApplicationDTO> applicationDTOList = getApplications(competitionId);
        List<SportsmanDTO> sportsmanDTOList = applicationService.getSportsmenFromApplications(applicationDTOList);
        return sportsmanDTOList;
    }

    /**
     * Запрос на получение всех регионов
     */
    @GetMapping("allRegions")
    List<RegionDTO> getRegions() {
        return generalMapper.fromRegion(generalService.getAllRegions());
    }

    /**
     * Запрос на получение всех регионов
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
     * Метод для вывода всех соревнований, у которых статус PAST
     */
    @GetMapping("/allPastCompetitions")
    public List<CompetitionDTO> getAllPastCompetition() {
        List<CompetitionDTO> competitionDTOList = competitionMapper.fromCompetition(generalService.getPresentCompetitions());
        return competitionDTOList;
    }

    /**
     * Метод для полечения всех новостей
     */
    @GetMapping("/getArticles")
    public ResponseEntity<List<ArticleDTO>> getAllArticles() throws IOException {
        List<ArticleDTO> articleDTOList = new ArrayList<ArticleDTO>();
        List<Article> articleList = articleService.getAllArticles();
        for (Article article: articleList) {
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
}









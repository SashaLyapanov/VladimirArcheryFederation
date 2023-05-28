package com.example.kursachrps.controllers;

import com.example.kursachrps.dto.AdditionalDTO.*;
import com.example.kursachrps.dto.ApplicationDTO;
import com.example.kursachrps.dto.CompetitionDTO;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.mapper.ApplicationMapper;
import com.example.kursachrps.mapper.CompetitionMapper;
import com.example.kursachrps.mapper.GeneralMapper;
import com.example.kursachrps.service.ApplicationService;
import com.example.kursachrps.service.GeneralService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
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

    public GeneralController (CompetitionMapper competitionMapper,
                              GeneralService generalService, ApplicationService applicationService, ApplicationMapper applicationMapper, GeneralMapper generalMapper) {
        this.competitionMapper = competitionMapper;
        this.generalService = generalService;
        this.applicationService = applicationService;
        this.applicationMapper = applicationMapper;
        this.generalMapper = generalMapper;
    }



    /**
     *Метод для вывода всех соревнований
     */
    @GetMapping("competitions")
    public List<CompetitionDTO> getCompetitions() {
        return competitionMapper.fromCompetition(generalService.showAllCompetitions());
    }

    /**
     * Метод для вывода соревнования по названию
     */
    @GetMapping("competition")
    public List<CompetitionDTO> getCompetitions(@RequestParam Date date) {
        return competitionMapper.fromCompetition(generalService.showCompetitionByDate(date));
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
    List<ApplicationDTO> getApplications(@RequestParam int competitionId) {

        List<ApplicationDTO> applicationDTOList = applicationMapper.fromApplication(applicationService.getApplicationsForCompetition(competitionId));
        return applicationDTOList;
    }


    /**
     * Метод для просмотра всех заявившихся спорстменов на определенные соревнования
     */
    @GetMapping("/declaredSportsmenForCompetition")
    List<SportsmanDTO> getDeclaredSportsmen(@RequestParam int competitionId) {
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
     * Запрос на получение всех квалификаций у тренеров
     */
    @GetMapping("allQualification")
    List<QualificationDTO> getAllQualifications() {
        return generalMapper.fromQualification(generalService.getAllQualifications());
    }

    /**
     * Запрос на получение всех команд
     */
    @GetMapping("allTeams")
    List<TeamDTO> getAllTeams() {
        return generalMapper.fromTeam(generalService.getAllTeams());
    }

    /**
     * Запрос на получение всех типов луков
     */
    @GetMapping("allBowTypes")
    List<BowTypeDTO> getAllBowType() {
        return generalMapper.fromBowType(generalService.getAllBowType());
    }


    /**
     * Метод для скачивания pdf протокола
     */
//    @GetMapping("/savePDFProtocol")
//    public ResponseEntity<Resource> savePDFProtocol(@RequestParam Date date) throws IOException {
//        //Реализация скачивания файла
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(fileName));
//        HttpHeaders header = new HttpHeaders();
//        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName.getName());
//        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
//        header.add("Pragma", "no-cache");
//        header.add("Expires", "0");
//        return ResponseEntity.ok()
//                .headers(header)
//                .contentLength(fileName.length())
//                .contentType(MediaType.parseMediaType("application/octet-stream"))
//                .body(resource);
//    }



    /**
     * Метод для вывода всех новостей в
     */



    /**
     * Метод для отображения определенной новости (страница этой новости)
     */
}









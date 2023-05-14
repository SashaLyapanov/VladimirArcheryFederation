package com.example.kursachrps.controllers;

import com.example.kursachrps.dto.ApplicationDTO;
import com.example.kursachrps.dto.CompetitionDTO;
import com.example.kursachrps.mapper.ApplicationMapper;
import com.example.kursachrps.mapper.CompetitionMapper;
import com.example.kursachrps.service.ApplicationService;
import com.example.kursachrps.service.GeneralService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/general")
public class GeneralController {

    private final CompetitionMapper competitionMapper;
    private final GeneralService generalService;
    private final ApplicationService applicationService;
    private final ApplicationMapper applicationMapper;

    public GeneralController (CompetitionMapper competitionMapper,
                              GeneralService generalService, ApplicationService applicationService, ApplicationMapper applicationMapper) {
        this.competitionMapper = competitionMapper;
        this.generalService = generalService;
        this.applicationService = applicationService;
        this.applicationMapper = applicationMapper;
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
//    @GetMapping("/applicationsForCompetition")
//    List<ApplicationDTO> getApplications(@RequestParam int competitionId) {
//
//        List<ApplicationDTO> applicationDTOList = applicationMapper.fromApplication(applicationService.getApplicationsForCompetition(competitionId));
//        return applicationDTOList;
//    }


    /**
     * Метод для просмотра всех заявившихся спорстменов на определенные соревнования
     */
}









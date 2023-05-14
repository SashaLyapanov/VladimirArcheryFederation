package com.example.kursachrps.controllers;

import com.example.kursachrps.Models.Application;
import com.example.kursachrps.dto.ApplicationDTO;
import com.example.kursachrps.mapper.ApplicationMapper;
import com.example.kursachrps.service.ApplicationService;
import com.example.kursachrps.service.CompetitionService;
import com.example.kursachrps.service.SportsmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sportsman")
public class SportsmanController {

    private final ApplicationMapper applicationMapper;
    private final ApplicationService applicationService;
    private final SportsmanService sportsmanService;
    private final CompetitionService competitionService;

    @Autowired
    public SportsmanController(ApplicationMapper applicationMapper,
                               ApplicationService applicationService,
                               SportsmanService sportsmanService,
                               CompetitionService competitionService) {
        this.applicationMapper = applicationMapper;
        this.applicationService = applicationService;
        this.sportsmanService = sportsmanService;
        this.competitionService = competitionService;
    }







    ///////////////////////////////////////////////////////////////////////////
    ///////////////////Методы, связанные c регистрацией на соревновния/////////
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Метод регистрации на соревнования
     * Логика такая, что спортсмен сначала выбирает соревнования! Проваливается в них (Это делается в дргом методе в GeneralController)
     * Дальше на странице соревнований будет кнопка зарегистрироваться, при нажатии на которую у нас происходи выполнение данного метода.
     * Должна генерироваться заявка на соревнования, которая связана вторичными ключами со спортсменом(1 к мн) и с соревнованиями(1 к мн)
     */
    @PostMapping("/regInCompetition")
    public void regInCompetition(@RequestParam int sportsmanId, @RequestParam int competitionId, @RequestBody ApplicationDTO applicationDTO) {

        Application application = applicationMapper.fromApplicationDTO(applicationDTO);
        sportsmanService.registrateSportsman(sportsmanId, competitionId, application);
    }


    /**
     * Просмотр всех собственных заявок на соревнования
     */
    @GetMapping("/allMyApplication")
    public List<ApplicationDTO> getMyApplications(@RequestParam int myId) {

        List<Application> applications = applicationService.getMyApplications(myId);
        List<ApplicationDTO> applicationDTOList = applicationMapper.fromApplication(applications);
        return applicationDTOList;
    }


    /**
     * Внести стартовый взнос
     */

}

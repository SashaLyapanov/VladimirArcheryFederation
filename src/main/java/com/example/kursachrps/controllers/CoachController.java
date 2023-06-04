package com.example.kursachrps.controllers;

import com.example.kursachrps.Models.Application;
import com.example.kursachrps.dto.ApplicationDTO;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.mapper.ApplicationMapper;
import com.example.kursachrps.mapper.SportsmanMapper;
import com.example.kursachrps.service.ApplicationService;
import com.example.kursachrps.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coach")
public class CoachController {

    private final CoachService coachService;
    private final SportsmanMapper sportsmanMapper;
    private final ApplicationMapper applicationMapper;
    private final ApplicationService applicationService;

    @Autowired
    public CoachController (CoachService coachService, SportsmanMapper sportsmanMapper, ApplicationMapper applicationMapper, ApplicationService applicationService) {
        this.coachService = coachService;
        this.sportsmanMapper = sportsmanMapper;
        this.applicationMapper = applicationMapper;
        this.applicationService = applicationService;
    }

    /**
     * Метод для выборки всех спортсменов, у которых тренер такой-то (по coach.email)
     */
    @GetMapping("/allMySportsmen")
    List<SportsmanDTO> getAllMySportsmen(@RequestParam String coachEmail) {
        List<SportsmanDTO> sportsmenDTO = sportsmanMapper.fromSportsmanToSportsmanDTO(coachService.findAllMySportsmen(coachEmail));
        return sportsmenDTO;
    }


    /**
     * Метод регистрации на соревнования
     * Логика такая, что тренер сначала выбирает соревнования! Проваливается в них (Это делается в другом методе в GeneralController)
     * Дальше на странице соревнований будет кнопка зарегистрироваться, при нажатии на которую у нас происходи выполнение данного метода.
     * Должна генерироваться заявка на соревнования, которая связана вторичными ключами со спортсменом(1 к мн) и с соревнованиями(1 к мн)
     */
    @PostMapping("/regInCompetition")
    public void regInCompetition(@RequestParam int coachId, @RequestParam int competitionId, @RequestBody ApplicationDTO applicationDTO) {

        Application application = applicationMapper.fromApplicationDTO(applicationDTO);
        coachService.registrateCoach(coachId, competitionId, application);
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

}

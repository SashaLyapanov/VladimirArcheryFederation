package com.example.kursachrps.controllers;

import com.example.kursachrps.models.Application;
import com.example.kursachrps.dto.ApplicationDTO;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.mapper.ApplicationMapper;
import com.example.kursachrps.mapper.SportsmanMapper;
import com.example.kursachrps.service.ApplicationService;
import com.example.kursachrps.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public String regInCompetition(@RequestParam String coachId, @RequestParam String competitionId, @RequestBody ApplicationDTO applicationDTO) throws JSONException, IOException, InterruptedException {
        if (applicationService.checkRegistrationInCompetition(competitionId, coachId)) {
            Application application = applicationMapper.fromApplicationDTO(applicationDTO);
            coachService.registrateCoach(coachId, competitionId, application);
            PayController payController = new PayController();
            String link =  payController.getLinkToPay();
            System.out.println(link);
            return link;
        }
        else
            return "Вы уже зарегистрированы на данных соревнованиях";


    }


    /**
     * Просмотр всех собственных заявок на соревнования
     */
    @GetMapping("/allMyApplication")
    public List<ApplicationDTO> getMyApplications(@RequestParam String myId) {

        List<Application> applications = applicationService.getMyApplications(myId);
        List<ApplicationDTO> applicationDTOList = applicationMapper.fromApplication(applications);
        return applicationDTOList;
    }

}

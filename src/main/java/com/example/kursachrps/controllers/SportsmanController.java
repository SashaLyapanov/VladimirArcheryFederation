package com.example.kursachrps.controllers;

import com.example.kursachrps.models.Application;
import com.example.kursachrps.models.Sportsman;
import com.example.kursachrps.dto.ApplicationDTO;
import com.example.kursachrps.dto.SportsmanMainDTO;
import com.example.kursachrps.mapper.ApplicationMapper;
import com.example.kursachrps.mapper.SportsmanMapper;
import com.example.kursachrps.service.ApplicationService;
import com.example.kursachrps.service.CompetitionService;
import com.example.kursachrps.service.SportsmanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sportsman")
public class SportsmanController {

    public static String UPLOAD_DIRECTORY = "C:/Users/-/IdeaProjects/KursachRPS/src/photo/AccountImage";

    private final ApplicationMapper applicationMapper;
    private final ApplicationService applicationService;
    private final SportsmanService sportsmanService;
    private final SportsmanMapper sportsmanMapper;
    private final CompetitionService competitionService;

    @Autowired
    public SportsmanController(ApplicationMapper applicationMapper,
                               ApplicationService applicationService,
                               SportsmanService sportsmanService,
                               SportsmanMapper sportsmanMapper, CompetitionService competitionService) {
        this.applicationMapper = applicationMapper;
        this.applicationService = applicationService;
        this.sportsmanService = sportsmanService;
        this.sportsmanMapper = sportsmanMapper;
        this.competitionService = competitionService;
    }


    ///////////////////////////////////////////////////////////////////////////
    ///////////////////Методы, связанные c регистрацией на соревновния/////////
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Метод регистрации на соревнования
     * Логика такая, что спортсмен сначала выбирает соревнования! Проваливается в них (Это делается в другом методе в GeneralController)
     * Дальше на странице соревнований будет кнопка зарегистрироваться, при нажатии на которую у нас происходи выполнение данного метода.
     * Должна генерироваться заявка на соревнования, которая связана вторичными ключами со спортсменом(1 к мн) и с соревнованиями(1 к мн)
     */
    @PostMapping("/regInCompetition")
    public String regInCompetition(@RequestParam int sportsmanId, @RequestParam int competitionId, @RequestBody ApplicationDTO applicationDTO) throws JSONException, IOException, InterruptedException {
        if (applicationService.checkRegistrationInCompetition(competitionId, sportsmanId)) {
            Application application = applicationMapper.fromApplicationDTO(applicationDTO);
            sportsmanService.registrateSportsman(sportsmanId, competitionId, application);
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
    public List<ApplicationDTO> getMyApplications(@RequestParam int myId) {

        List<Application> applications = applicationService.getMyApplications(myId);
        List<ApplicationDTO> applicationDTOList = applicationMapper.fromApplication(applications);
        return applicationDTOList;
    }


    ///////////////////////////////////////////////////////////////////////////
    ///////////////////Методы, связанные ЛК////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////


    /**
     * Метод для редактирования личного кабинета
     */
    @PutMapping("/editProfile")
    public SportsmanMainDTO editProfile(@RequestParam int id, @RequestBody @Valid SportsmanMainDTO sportsmanMainDTO) {

        if (sportsmanMainDTO.getPassword() != null) {
            sportsmanService.hashPassword(sportsmanMainDTO);
        }
        Sportsman sportsman = sportsmanMapper.fromSportsmanMainDTO(sportsmanMainDTO);
        sportsmanService.editProfile(id, sportsman);

         return sportsmanMainDTO;
    }

    /**
     * Метод для подгрузки фотографии в личный кабинет
     */
    @PostMapping("/uploadImage")
    public void uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
    }



    /**
     * Внести стартовый взнос
     */

}

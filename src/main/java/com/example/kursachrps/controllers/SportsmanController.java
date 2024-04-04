package com.example.kursachrps.controllers;

import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.models.Application;
import com.example.kursachrps.models.SportsTitle;
import com.example.kursachrps.models.Sportsman;
import com.example.kursachrps.dto.ApplicationDTO;
import com.example.kursachrps.dto.SportsmanMainDTO;
import com.example.kursachrps.mapper.ApplicationMapper;
import com.example.kursachrps.mapper.SportsmanMapper;
import com.example.kursachrps.repositories.SportsTitleRepository;
import com.example.kursachrps.service.ApplicationService;
import com.example.kursachrps.service.SportsmanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private final SportsTitleRepository sportsTitleRepository;

    @Autowired
    public SportsmanController(ApplicationMapper applicationMapper,
                               ApplicationService applicationService,
                               SportsmanService sportsmanService,
                               SportsmanMapper sportsmanMapper,
                               SportsTitleRepository sportsTitleRepository) {
        this.applicationMapper = applicationMapper;
        this.applicationService = applicationService;
        this.sportsmanService = sportsmanService;
        this.sportsmanMapper = sportsmanMapper;
        this.sportsTitleRepository = sportsTitleRepository;
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
    public String regInCompetition(@RequestParam String sportsmanId, @RequestParam String competitionId, @RequestBody ApplicationDTO applicationDTO) throws JSONException, IOException, InterruptedException {
        if (applicationService.checkRegistrationInCompetition(competitionId, sportsmanId)) {
            Application application = applicationMapper.fromApplicationDTO(applicationDTO);
            sportsmanService.registrateSportsman(sportsmanId, competitionId, application);
            PayController payController = new PayController();
            String link = payController.getLinkToPay();
            System.out.println(link);
            return link;
        } else
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
    public void uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
    }


    /**
     * Поиск всех спортсменов, зарегистрированных на определенные соревнования по id соревнования и типу лука
     */
    @GetMapping("/sportsmenByCompetitionAndBowType")
    public List<SportsmanDTO> getAllSportsmanByCompetitionAndBowType(@RequestParam String id,
                                                                     @RequestParam String bowTypeName) {
        List<SportsmanDTO> list = sportsmanService.getAllSportmanByCompetitionAndBowType(id, bowTypeName);
        return list;
    }

    /**
     * Получение всех спортивных титулов, у которых название содержит входной параметр name
     */
    @GetMapping("/titles")
    public Page<SportsTitle> getSportTitles(@PageableDefault(page = 0, size = 10) Pageable pageable,
                                            @RequestParam String name) {
        SportsTitle searchPattern = new SportsTitle();
        if (name != null && !name.isEmpty()) {
            searchPattern.setName(name);
        }
        Page<SportsTitle> resultPage = sportsTitleRepository.findAll(searchPattern, pageable);
        return resultPage;
    }

    /**
     * Получение всех спортивных титулов, у которых название содержит входной параметр name с пагинацией!
     */
    @GetMapping("/titlesWithPagination")
    public List<SportsTitle> getSportTitlesWithPagination(@RequestParam String name,
                                                          @RequestParam(required = false, defaultValue = "0") int page,
                                                          @RequestParam(required = false, defaultValue = "10") int size) {
        SportsTitle searchPattern = new SportsTitle();
        if (name != null && !name.isEmpty()) {
            searchPattern.setName(name);
        }
        Page<SportsTitle> resultPage = sportsTitleRepository.findAll(searchPattern, PageRequest.of(page, size));
        return resultPage.getContent();
    }


}

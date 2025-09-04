package com.example.kursachrps.controllers;

import com.example.kursachrps.models.Application;
import com.example.kursachrps.models.SportsTitle;
import com.example.kursachrps.dto.ApplicationDTO;
import com.example.kursachrps.mapper.ApplicationMapper;
import com.example.kursachrps.repositories.SportsTitleRepository;
import com.example.kursachrps.service.ApplicationService;
import com.example.kursachrps.service.SportsmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sportsman")
public class SportsmanController {

    private final ApplicationMapper applicationMapper;
    private final ApplicationService applicationService;
    private final SportsmanService sportsmanService;
    private final SportsTitleRepository sportsTitleRepository;

    @Autowired
    public SportsmanController(ApplicationMapper applicationMapper,
                               ApplicationService applicationService,
                               SportsmanService sportsmanService,
                               SportsTitleRepository sportsTitleRepository) {
        this.applicationMapper = applicationMapper;
        this.applicationService = applicationService;
        this.sportsmanService = sportsmanService;
        this.sportsTitleRepository = sportsTitleRepository;
    }


    ///////////////////////////////////////////////////////////////////////////
    //      Методы, связанные c регистрацией на соревновния     //
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Метод регистрации на соревнования
     * Логика такая, что спортсмен сначала выбирает соревнования! Проваливается в них (Это делается в другом методе в GeneralController)
     * Дальше на странице соревнований будет кнопка зарегистрироваться, при нажатии на которую у нас происходи выполнение данного метода.
     * Должна генерироваться заявка на соревнования, которая связана вторичными ключами со спортсменом(1 к мн) и с соревнованиями(1 к мн)
     */
    @PostMapping("/regInCompetition")
    @PreAuthorize("hasAuthority('ROLE_SPORTSMAN')")
    public ResponseEntity<?> regInCompetition(@RequestParam String sportsmanId, @RequestParam String competitionId, @RequestBody ApplicationDTO applicationDTO) {
        if (applicationService.checkRegistrationInCompetition(competitionId, sportsmanId)) {
            Application application = applicationMapper.fromApplicationDTO(applicationDTO);
            sportsmanService.registrateSportsman(sportsmanId, competitionId, application);
            return ResponseEntity.status(HttpStatus.OK).body("Успешная регистрация");
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Вы уже зарегистрированы на данное мероприятие");
    }

    /**
     * Проверка на уже существующую регистрацию на определенные соревнования у спортсмена по CompetitinoId и UserId
     */
    @GetMapping("/checkApplication")
    @PreAuthorize("hasAuthority('ROLE_SPORTSMAN')")
    public ResponseEntity<?> checkExistApplication(@RequestParam String sportsmanId, @RequestParam String competitionId) {
        boolean response = applicationService.checkRegistrationInCompetition(competitionId, sportsmanId);
        if (response) {
            return ResponseEntity.status(HttpStatus.OK).body("true");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("false");
        }
    }

    /**
     * Просмотр всех собственных заявок на соревнования
     */
    @GetMapping("/allMyApplication")
    @PreAuthorize("hasAuthority('ROLE_SPORTSMAN')")
    public List<ApplicationDTO> getMyApplications(@RequestParam String myId) {

        List<Application> applications = applicationService.getMyApplications(myId);
        List<ApplicationDTO> applicationDTOList = applicationMapper.fromApplication(applications);
        return applicationDTOList;
    }

    /**
     * Отмена заявки
     */
    @PostMapping("deleteApplication")
    @PreAuthorize("hasAuthority('ROLE_SPORTSMAN')")
    public void deleteMyApplication(@RequestParam String sportsmanId, @RequestParam String competitionId) {
        applicationService.deleteMyApplication(sportsmanId, competitionId);
    }

    /**
     * Поиск всех спортсменов, зарегистрированных на определенные соревнования по id соревнования и типу лука
     */
    @GetMapping("/sportsmenByCompetitionAndBowType")
    @PreAuthorize("hasAnyAuthority('ROLE_SPORTSMAN', 'ROLE_ADMIN')")
    public List<ApplicationDTO> getAllSportsmanByCompetitionAndBowType(@RequestParam String id,
                                                                       @RequestParam String bowTypeName) {
        List<ApplicationDTO> list = sportsmanService.getAllSportmanByCompetitionAndBowType(id, bowTypeName);
        return list;
    }

    /**
     * Получение всех спортивных титулов, у которых название содержит входной параметр name
     */
    @GetMapping("/titles")
    @PreAuthorize("hasAuthority('ROLE_SPORTSMAN')")
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
    @PreAuthorize("hasAuthority('ROLE_SPORTSMAN')")
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

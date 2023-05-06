package com.example.kursachrps.controllers;

import com.example.kursachrps.Models.Sportsman;
import com.example.kursachrps.Models.User;
import com.example.kursachrps.dto.CompetitionDTO;
import com.example.kursachrps.dto.UserDTO;
import com.example.kursachrps.mapper.CompetitionMapper;
import com.example.kursachrps.mapper.UserMapper;
import com.example.kursachrps.repositories.CompetitionRepository;
import com.example.kursachrps.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserMapper userMapper;
    private final CompetitionRepository competitionRepository;
    private final CompetitionMapper competitionMapper;

    @Autowired
    public AdminController(AdminService adminService, UserMapper userMapper, CompetitionRepository competitionRepository, CompetitionMapper competitionMapper) {
        this.adminService = adminService;
        this.userMapper = userMapper;
        this.competitionRepository = competitionRepository;
        this.competitionMapper = competitionMapper;
    }


    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////////Реализация CRUD спортсменов//////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////


    /**
     * Метод для вывода всех User'ов споском
     */
    @GetMapping("sportsmen")
    public List<UserDTO> getAllSportsmen() {

        List<User> sportsmen = new ArrayList<>();
        sportsmen = adminService.showAllUsers();
        List<UserDTO> dto = userMapper.fromUser(sportsmen);

        return dto;
    }


    /**
     * Метод для создания спортсмена в системе (регистрация от Админа)
     */
    @PostMapping("createSportsman")
    public Sportsman createSportsman(@RequestBody @Valid UserDTO userDTO) {

        Sportsman sportsman = userMapper.fromUserDTO(userDTO);
        return adminService.saveSportsman(sportsman);
    }

    @GetMapping("sportsman/{email}")
    public User getSportsman(@PathVariable String email) {
        User user = adminService.getSportsman(email);

        return user;
    }



    /**
     *Метод для вывода всех соревнований
     */
    @GetMapping("competitions")
    public List<CompetitionDTO> getCompetitions() {
        return competitionMapper.fromCompetition(competitionRepository.findAll());
    }

    /////////////////////////////////////////////////////////////////////////////////
    //////////Тестовые методы для проверки работоспособности/////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    @PostMapping("create")
    public User createUserLikeSportsman(@RequestBody @Valid UserDTO userDTO) {
        User user = userMapper.convert(userDTO);
        user = adminService.save(user);

        return user;
    }

    @GetMapping("info/{email}")
    public UserDTO showInfo(@PathVariable String email) throws ChangeSetPersister.NotFoundException {
        User user = adminService.show(email).orElseThrow(ChangeSetPersister.NotFoundException::new);
        UserDTO userDTO = userMapper.transform(user);

        return userDTO;
    }
}

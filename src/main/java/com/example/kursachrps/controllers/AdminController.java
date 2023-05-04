package com.example.kursachrps.controllers;

import com.example.kursachrps.Models.Sportsman;
import com.example.kursachrps.Models.User;
import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.dto.UserDTO;
import com.example.kursachrps.mapper.UserMapper;
import com.example.kursachrps.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserMapper userMapper;

    @Autowired
    public AdminController(AdminService adminService, UserMapper userMapper) {
        this.adminService = adminService;
        this.userMapper = userMapper;
    }


    /////////////////////////////////////////////////////////////////////////////////
    ////////////////////////Реализация CRUD спортсменов//////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////


//    /**
//     * Метод для вывода всех спортсменов споском
//     */
//    @GetMapping("sportsmen")
//    public List<Sportsman> getAllSportsmen() {
//
//        List<Sportsman> sportsmen = new ArrayList<>();
//        sportsmen = adminService.showAllSportsmen();
//        SportsmanDTO sportsmenDTO = spo
//
//        return sportsmenDTO;
//    }


    /**
     * Метод для создания спортсмена в системе (регистрация от Админа)
     */
    @PostMapping("createSportsman")
    public Sportsman createSportsman(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Надо сделать обработку ошибок");
        }

        User user = userMapper.convert(userDTO);
        return adminService.saveSportsman(user);
    }

    @GetMapping("sportsman/{email}")
    public User getSportsman(@PathVariable String email) {
//        if (bindingResult.hasErrors()) {
//            System.out.println("Надо сделать обработку ошибок");
//        }

        User user = adminService.getSportsman(email);
        return user;

    }


    /////////////////////////////////////////////////////////////////////////////////
    //////////Тестовые методы для проверки работоспособности/////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    @PostMapping("create")
    public User createUserLikeSportsman(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Не, ну и фигню вы передали в JSON!!!");
        }

        User user = userMapper.convert(userDTO);
        user = adminService.save(user);
        return user;
    }

    @GetMapping("info/{email}")
    public UserDTO showInfo(@PathVariable String email) {

        User user = adminService.show(email).orElse(new User());
        UserDTO userDTO = userMapper.transform(user);

        return userDTO;
    }
}

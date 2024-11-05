package com.example.kursachrps.controllers;

import com.example.kursachrps.dto.SportsmanDTO;
import com.example.kursachrps.mapper.SportsmanMapper;
import com.example.kursachrps.models.Sportsman;
import com.example.kursachrps.service.PersonalAccountSportsmanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/personalAccount")
public class PersonalAccountSportsmanController {

    private final PersonalAccountSportsmanService personalAccountSportsmanService;
    private final SportsmanMapper sportsmanMapper;

    @Autowired
    public PersonalAccountSportsmanController(PersonalAccountSportsmanService personalAccountSportsmanService, SportsmanMapper sportsmanMapper) {
        this.personalAccountSportsmanService = personalAccountSportsmanService;
        this.sportsmanMapper = sportsmanMapper;
    }

    /**
     * Получение данных личного кабинета
     */
    @GetMapping("/myProfileData")
    public SportsmanDTO getPersonalAccountBySportsmanId(@RequestParam String sportsmanId) {
        return sportsmanMapper.fromSportsman(personalAccountSportsmanService.getSportsmanById(sportsmanId));
    }

    /**
     * Редактирование личного кабинета
     */
    @PutMapping("/editProfile")
    public SportsmanDTO editProfile(@RequestParam String sportsmanId, @RequestBody @Valid SportsmanDTO sportsmanDTO) {
        if (sportsmanDTO.getPassword() != null) {
            personalAccountSportsmanService.hashPassword(sportsmanDTO);
        }
        Sportsman sportsman = sportsmanMapper.fromSportsmanDTO(sportsmanDTO);
        personalAccountSportsmanService.editProfile(sportsmanId, sportsman);

        return sportsmanDTO;
    }

    /**
     * Метод для подгрузки фотографии в личный кабинет
     */
    @PostMapping("/uploadImage")
    public void uploadImage(@RequestParam String sportsmanId, @RequestParam MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            personalAccountSportsmanService.uploadAvatarImage(sportsmanId, file);
        } else {
            System.out.println("Вы не передали никакого файла");
        }
    }

}

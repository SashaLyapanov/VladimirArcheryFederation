package com.example.kursachrps.controllers;

import com.example.kursachrps.Models.Application;
import com.example.kursachrps.dto.ApplicationDTO;
import com.example.kursachrps.dto.CompetitionDTO;
import com.example.kursachrps.mapper.ApplicationMapper;
import com.example.kursachrps.mapper.CompetitionMapper;
import com.example.kursachrps.service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/judge")
public class JudgeController {

    private JudgeService judgeService;
    private CompetitionMapper competitionMapper;
    private ApplicationMapper applicationMapper;

    @Autowired
    public JudgeController(JudgeService judgeService,
                           CompetitionMapper competitionMapper,
                           ApplicationMapper applicationMapper) {
        this.judgeService = judgeService;
        this.competitionMapper = competitionMapper;
        this.applicationMapper = applicationMapper;
    }


    @GetMapping("/generateProtocol")
    public void generateProtocol(@RequestParam int competitionId) throws IOException {
        judgeService.generateProtocol(competitionId);
    }


    /**
     * Запрос на выборку соревнований, где status = Present
     */
    @GetMapping("/presentCompetitions")
    public List<CompetitionDTO> getPresentCompetitions() {
        List<CompetitionDTO> competitionDTOList = competitionMapper.fromCompetition(judgeService.getPresentCompetitions());
        return competitionDTOList;
    }


    /**
     * Метод для регистрации спортсменов или тренеров на соревнования
     */
    @PostMapping("/regParticipantToCompetition")
    public void regParticipantToCompetition(@RequestParam int competitionId, @RequestParam String email, @RequestBody ApplicationDTO applicationDTO) {

        Application application = applicationMapper.fromApplicationDTO(applicationDTO);
        judgeService.registrateParticipantToCompetition(email, competitionId, application);
    }

}
























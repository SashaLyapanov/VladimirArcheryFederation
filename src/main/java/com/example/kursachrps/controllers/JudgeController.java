package com.example.kursachrps.controllers;

import com.example.kursachrps.dto.CompetitionDTO;
import com.example.kursachrps.mapper.CompetitionMapper;
import com.example.kursachrps.service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/judge")
public class JudgeController {

    private JudgeService judgeService;
    private CompetitionMapper competitionMapper;

    @Autowired
    public JudgeController(JudgeService judgeService,
                           CompetitionMapper competitionMapper) {
        this.judgeService = judgeService;
        this.competitionMapper = competitionMapper;
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

}
























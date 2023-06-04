package com.example.kursachrps.controllers;

import com.example.kursachrps.Models.Application;
import com.example.kursachrps.dto.ApplicationDTO;
import com.example.kursachrps.dto.CompetitionDTO;
import com.example.kursachrps.mapper.ApplicationMapper;
import com.example.kursachrps.mapper.CompetitionMapper;
import com.example.kursachrps.service.ApplicationService;
import com.example.kursachrps.service.JudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/judge")
public class JudgeController {

    private JudgeService judgeService;
    private CompetitionMapper competitionMapper;
    private ApplicationMapper applicationMapper;
    private ApplicationService applicationService;

    @Autowired
    public JudgeController(JudgeService judgeService,
                           CompetitionMapper competitionMapper,
                           ApplicationMapper applicationMapper,
                           ApplicationService applicationService) {
        this.judgeService = judgeService;
        this.competitionMapper = competitionMapper;
        this.applicationMapper = applicationMapper;
        this.applicationService = applicationService;
    }


    /**
     * Метод для генерирования протокола и автоматического скачивания файла на локальный пк пользователя
     */
    @GetMapping("/generateProtocol")
    public ResponseEntity<Resource> generateProtocol(@RequestParam int competitionId) throws IOException {
        File fileName = judgeService.generateProtocol(competitionId);

        //Реализация скачивания файла
        InputStreamResource resource = new InputStreamResource(new FileInputStream(fileName));
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName.getName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(fileName.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    /**
     * Метод для загрузки отредактированного файла на сервер. И также сразу конвертирование в pdf.
     */
    @PostMapping("/uploadFile")
    public void handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam int competitionId) throws Exception {
        String path = judgeService.uploadFile(file);
        String newFile = judgeService.convertXLSXToPDF(path);
        judgeService.changeStatusOfCompetition(competitionId);
        judgeService.addPathFileInCompetition(competitionId, newFile);
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
    public String regParticipantToCompetition(@RequestParam int competitionId, @RequestParam String email, @RequestBody ApplicationDTO applicationDTO) throws JSONException, IOException, InterruptedException {
        if (applicationService.checkRegistrationInCompetition(competitionId, email)) {
            Application application = applicationMapper.fromApplicationDTO(applicationDTO);
            judgeService.registrateParticipantToCompetition(email, competitionId, application);
            PayController payController = new PayController();
            String link =  payController.getLinkToPay();
            System.out.println(link);
            return link;
        }
        else
            return "Участник уже зарегистрирован на данные соревнования";
    }

}
























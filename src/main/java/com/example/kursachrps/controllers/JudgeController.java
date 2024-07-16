package com.example.kursachrps.controllers;

import com.example.kursachrps.models.Application;
import com.example.kursachrps.dto.ApplicationDTO;
import com.example.kursachrps.dto.CompetitionDTO;
import com.example.kursachrps.mapper.ApplicationMapper;
import com.example.kursachrps.mapper.CompetitionMapper;
import com.example.kursachrps.service.ApplicationService;
import com.example.kursachrps.service.JudgeService;
import com.example.kursachrps.service.ProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/judge")
public class JudgeController {

    private final JudgeService judgeService;
    private final CompetitionMapper competitionMapper;
    private final ApplicationMapper applicationMapper;
    private final ApplicationService applicationService;
    private final ProtocolService protocolService;

    @Autowired
    public JudgeController(JudgeService judgeService,
                           CompetitionMapper competitionMapper,
                           ApplicationMapper applicationMapper,
                           ApplicationService applicationService,
                           ProtocolService protocolService) {
        this.judgeService = judgeService;
        this.competitionMapper = competitionMapper;
        this.applicationMapper = applicationMapper;
        this.applicationService = applicationService;
        this.protocolService = protocolService;
    }

    /**
     * Получение соревнований, где status = Present
     * Именно с этими соревнованиями может работать судья
     * !Других соревнований он не видит
     */
    @GetMapping("/presentCompetitions")
    public List<CompetitionDTO> getPresentCompetitions() {
        return competitionMapper.fromCompetition(judgeService.getPresentCompetitions());
    }

    /**
     * Метод для регистрации спортсменов или тренеров на соревнования
     */
    @PostMapping("/regParticipantToCompetition")
    public void regParticipantToCompetition(@RequestParam String competitionId, @RequestParam String email, @RequestBody ApplicationDTO applicationDTO) {
        if (applicationService.checkRegistrationInCompetitionByParticipantEmail(competitionId, email)) {
            Application application = applicationMapper.fromApplicationDTO(applicationDTO);
            judgeService.registrateParticipantToCompetition(email, competitionId, application);
//            PayController payController = new PayController();
//            String link = payController.getLinkToPay();
//            System.out.println(link);
//            return link;
        }
    }

    /**
     * Метод для генерирования протокола и автоматического скачивания файла на локальный пк пользователя
     */
    @GetMapping("/generateProtocol")
    public ResponseEntity<Resource> generateProtocol(@RequestParam String competitionId) throws IOException {
        judgeService.markExtraStages(competitionId);
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
     * Метод для загрузки отредактированного файла с результатами квалификации на сервер.
     * Автоматическакя генерация следующих этапов соревнований и загрузка файла на компьютер в папку Загрузки
     */
    @PostMapping("/uploadFile")
    @Transactional
    public ResponseEntity<?> uploadQualificationFile(@RequestParam("file") MultipartFile file, @RequestParam String competitionId) throws IOException {
        if (!protocolService.checkQualificationIsCompleted(competitionId)) {
            File protocol = judgeService.uploadQualificationProtocol(file, competitionId);
            if (protocol != null) {
                boolean resultOfGenerating = judgeService.generateNextStageOfCompetitionAfterQualification(protocol, competitionId);
                if (resultOfGenerating) {
                    //TODO
                    // Реализовать скачивание протокола в загрузки компьютера
                    return ResponseEntity.ok("Все хорошо, квалификация загружена!");
                } else {
                    return ResponseEntity.badRequest().body("Что-то пошло не так при генерации следующей стадии протокола");
                }
            }
            return ResponseEntity.badRequest().body("Что-то пошло не так при генерации следующей стадии протокола");
        } else {
            File protocol = judgeService.uploadProtocolWithSomeStage(file, competitionId);
            boolean resultOfGenerating = judgeService.generateNextStageOfCompetition(protocol, competitionId);
            if (resultOfGenerating) {
                //TODO
                // Реализовать скачивание протокола в загрузки компьютера
                return ResponseEntity.ok("Все хорошо, различные стадии финала загружены!");
            } else {
                return ResponseEntity.badRequest().body("Что-то пошло не так при генерации следующей стадии протокола");
            }
        }
    }


//    /**
//     * Метод для загрузки отредактированного файла с результатами квалификации на сервер.
//     * Автоматическакя генерация следующих этапов соревнований и загрузка файла на компьютер в папку Загрузки
//     */
//    @PostMapping("/uploadFile")
//    @Transactional
//    public ResponseEntity<?> uploadQualificationFile(@RequestParam("file") MultipartFile file, @RequestParam String competitionId) throws IOException {
//        File protocol = judgeService.uploadQualificationProtocol(file, competitionId);
//        if (protocol != null) {
//            boolean resultOfGenerating = judgeService.generateNextStageOfCompetition(protocol, competitionId);
//            if (resultOfGenerating) {
//                return ResponseEntity.ok("Все хорошо, так держать!");
//            } else {
//                return ResponseEntity.badRequest().body("Что-то пошло не так при генерации следующей стадии протокола");
//            }
//        }
//        return ResponseEntity.badRequest().body("Что-то пошло не так при генерации следующей стадии протокола");
//    }

}
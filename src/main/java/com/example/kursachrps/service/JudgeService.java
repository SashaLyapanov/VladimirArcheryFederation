package com.example.kursachrps.service;

import com.example.kursachrps.ExcelGenerator2;
import com.example.kursachrps.Models.Application;
import com.example.kursachrps.Models.Competition;
import com.example.kursachrps.repositories.ApplicationRepository;
import com.example.kursachrps.repositories.CompetitionRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

@Service
public class JudgeService {

    private ApplicationRepository applicationRepository;
    private CompetitionRepository competitionRepository;

    private ExcelGenerator2 excelGenerator2 = new ExcelGenerator2();


    @Autowired
    public JudgeService(ApplicationRepository applicationRepository, CompetitionRepository competitionRepository) {
        this.applicationRepository = applicationRepository;
        this.competitionRepository = competitionRepository;
    }

    /**
     * Метод для генерации EXCEL протокола 3D соревнований.
     */
    public void generateProtocol(int competitionId) throws IOException {
        //Это наш шаблон, чтобы скопировать его в новый файл
        File file = new File("C:\\Users\\-\\IdeaProjects\\KursachRPS\\src\\filesExcel\\TestPattern.xlsx");
        //Создадим новый файл
        LocalDate today = LocalDate.now();
        File protocol = new File("C:/Users/-/IdeaProjects/KursachRPS/src/filesExcel/" + today + ".xlsx");
        try {
            boolean created = protocol.createNewFile();
            if (created) {
                System.out.println("Ес, файл создался");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if (protocol.exists()) {
            InputStream is = null;
            OutputStream os = null;
            try {
                is = new FileInputStream(file);
                os = new FileOutputStream(protocol);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            } finally {
                assert is != null;
                is.close();
                assert os != null;
                os.close();
            }

//            List<Sportsman> sportsmen = sportsmanMainRepository.findAll();
            List<Application> applications = applicationRepository.findApplicationByCompetitionId(competitionId);

            try {
                excelGenerator2.appendRows(applications, protocol);
            } catch (IOException | InvalidFormatException e) {
                e.printStackTrace();
            }
            System.out.println("Протокол успешно создан");
        }
    }


    /**
     * Метод для вывода списка соревнований, где status = Present
     */
    public List<Competition> getPresentCompetitions() {
        return competitionRepository.findAllPresent();
    }
}

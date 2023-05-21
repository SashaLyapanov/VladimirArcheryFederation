package com.example.kursachrps.service;

import com.example.kursachrps.ExcelGenerator2;
import com.example.kursachrps.Models.Sportsman;
import com.example.kursachrps.Models.User;
import com.example.kursachrps.repositories.SportsmanMainRepository;
import com.example.kursachrps.repositories.UserMainRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class JudgeService {

    private SportsmanMainRepository sportsmanMainRepository;
    private ExcelGenerator2 excelGenerator2 = new ExcelGenerator2();

    @Autowired
    public JudgeService(SportsmanMainRepository sportsmanMainRepository) {
        this.sportsmanMainRepository = sportsmanMainRepository;
    }


    public void generateProtocol() throws IOException {
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

            List<Sportsman> sportsmen = sportsmanMainRepository.findAll();

            try {
                excelGenerator2.appendRows(sportsmen, protocol);
            } catch (IOException | InvalidFormatException e) {
                e.printStackTrace();
            }
            System.out.println("Протокол успешно создан");
        }
    }
}

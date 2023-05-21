package com.example.kursachrps.controllers;

import com.example.kursachrps.ExcelGenerator;
import com.example.kursachrps.Models.User;
import com.example.kursachrps.service.JudgeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/judge")
public class JudgeController {

    private JudgeService judgeService;

    @Autowired
    public JudgeController(JudgeService judgeService) {
        this.judgeService = judgeService;
    }


    @GetMapping("/generateProtocol")
    public void generateProtocol() throws IOException {
        judgeService.generateProtocol();
    }

}
























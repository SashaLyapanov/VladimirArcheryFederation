package com.example.kursachrps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class KursachRpsApplication {

    public static void main(String[] args) { SpringApplication.run(KursachRpsApplication.class, args);

    }
}

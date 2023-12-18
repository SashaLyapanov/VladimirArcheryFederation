package com.example.kursachrps.service;

import com.example.kursachrps.models.Article;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class FileService {


    public void saveImage(Article article) {
        MultipartFile file = article.getFile();

//        LocalDateTime dateTime = LocalDateTime.now();

        Path filepath = Paths.get("C:\\Users\\-\\IdeaProjects\\VladimirArcheryFederation\\src\\imageForArticles", file.getOriginalFilename());
//        Path filepath = Paths.get("C:\\Users\\-\\IdeaProjects\\VladimirArcheryFederation\\src\\imageForArticles", dateTime.toString() + file.getOriginalFilename());

        article.setLink(filepath.toString());

        try {
            OutputStream os = Files.newOutputStream(filepath);
            os.write(file.getBytes());
        } catch (IOException e) {
            System.out.println("Ну это фиаско братан: " + e.getMessage());
        }
        System.out.println("Файл успешно загружен и сохранен по пути: " + filepath);
    }
}

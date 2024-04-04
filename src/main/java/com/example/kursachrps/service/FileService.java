package com.example.kursachrps.service;

import com.example.kursachrps.models.AboutFederation;
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

    public void saveFilesForAboutFederation(AboutFederation aboutFederation) {
        MultipartFile file1 = aboutFederation.getRegulation();
        MultipartFile file2 = aboutFederation.getHistory();

        Path filepath1 = Paths.get("C:\\Users\\-\\IdeaProjects\\VladimirArcheryFederation\\src\\filesAboutFederation", file1.getOriginalFilename());
        Path filepath2 = Paths.get("C:\\Users\\-\\IdeaProjects\\VladimirArcheryFederation\\src\\filesAboutFederation", file2.getOriginalFilename());

        aboutFederation.setLinkForRegulation(filepath1.toString());
        aboutFederation.setLinkForHistory(filepath2.toString());

        try {
            OutputStream os1 = Files.newOutputStream(filepath1);
            OutputStream os2 = Files.newOutputStream(filepath2);
            os1.write(file1.getBytes());
            os2.write(file2.getBytes());
        } catch (IOException e) {
            System.out.println("Файлы не удалось сохранить: " + e.getMessage());
        }

        System.out.println("Файлы успешно загружены и сохранены");
    }

}

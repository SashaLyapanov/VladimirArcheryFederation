package com.example.kursachrps.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "articles")
public class Article extends GenericEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "body")
    private String body;

    @Column(name = "date")
    private Date dateTime;

    @Transient
    private MultipartFile file;

    @Column(name = "link")
    private String link;

    @PrePersist
    private void init() {
        dateTime = new Date();
    }

    @PostLoad
    public void loadFileLink() {
        if (link != null) {
            try {
                File fileFromLink = ResourceUtils.getFile(link);
                String originalFilename = fileFromLink.getName();
                String contentType = "multipart/form-data";
                byte[] content = Files.readAllBytes(fileFromLink.toPath());
                file = new MockMultipartFile(link, originalFilename, contentType, content);
            } catch (FileNotFoundException e) {
                System.out.println("Мы столкнулись с FileNotFoundException: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Мы столкнулись с IOException: " + e.getMessage());
            }
        }
    }

}

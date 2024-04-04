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

@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "about_federation")
public class AboutFederation extends GenericEntity{

    /**
     * Руководство федерации
     */
    @Column(name = "managers")
    private String managers;

    /**
     * Контактная информация
     */
    @Column(name = "contacts")
    private String contacts;

    /**
     * Поле для файла с уставом
     */
    @Transient
    private MultipartFile regulation;

    @Column(name = "linkForRegulation")
    private String linkForRegulation;

    /**
     * Поле для файла с уставом
     */
    @Transient
    private MultipartFile history;

    @Column(name = "linkForHistory")
    private String linkForHistory;

    @PostLoad
    public void loadFileLink() {
        if (linkForRegulation != null) {
            try {
                File fileFromLink = ResourceUtils.getFile(linkForRegulation);
                String originalFilename = fileFromLink.getName();
                String contentType = "multipart/form-data";
                byte[] content = Files.readAllBytes(fileFromLink.toPath());
                regulation = new MockMultipartFile(linkForRegulation, originalFilename, contentType, content);
            } catch (FileNotFoundException e) {
                System.out.println("Мы столкнулись с FileNotFoundException: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Мы столкнулись с IOException: " + e.getMessage());
            }
        }
        if (linkForHistory != null) {
            try {
                File fileFromLink = ResourceUtils.getFile(linkForHistory);
                String originalFilename = fileFromLink.getName();
                String contentType = "multipart/form-data";
                byte[] content = Files.readAllBytes(fileFromLink.toPath());
                history = new MockMultipartFile(linkForHistory, originalFilename, contentType, content);
            } catch (FileNotFoundException e) {
                System.out.println("Мы столкнулись с FileNotFoundException: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Мы столкнулись с IOException: " + e.getMessage());
            }
        }
    }

}


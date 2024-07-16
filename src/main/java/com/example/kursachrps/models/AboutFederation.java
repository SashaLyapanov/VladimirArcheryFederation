package com.example.kursachrps.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

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
     * Список названий подгруженных файлов через запятую
     */
    @Column(name = "list_file_names")
    private String listFileNames;

    /**
     * Список ссылок на ресурсы через запятую
     */
    @Column(name = "list_links")
    private String listLinks;

//    @PostLoad
//    public void loadFileLink() {
//        if (linkForRegulation != null) {
//            try {
//                File fileFromLink = ResourceUtils.getFile(linkForRegulation);
//                String originalFilename = fileFromLink.getName();
//                String contentType = "multipart/form-data";
//                byte[] content = Files.readAllBytes(fileFromLink.toPath());
//                regulation = new MockMultipartFile(linkForRegulation, originalFilename, contentType, content);
//            } catch (FileNotFoundException e) {
//                System.out.println("Мы столкнулись с FileNotFoundException: " + e.getMessage());
//            } catch (IOException e) {
//                System.out.println("Мы столкнулись с IOException: " + e.getMessage());
//            }
//        }
//        if (linkForHistory != null) {
//            try {
//                File fileFromLink = ResourceUtils.getFile(linkForHistory);
//                String originalFilename = fileFromLink.getName();
//                String contentType = "multipart/form-data";
//                byte[] content = Files.readAllBytes(fileFromLink.toPath());
//                history = new MockMultipartFile(linkForHistory, originalFilename, contentType, content);
//            } catch (FileNotFoundException e) {
//                System.out.println("Мы столкнулись с FileNotFoundException: " + e.getMessage());
//            } catch (IOException e) {
//                System.out.println("Мы столкнулись с IOException: " + e.getMessage());
//            }
//        }
//    }

}


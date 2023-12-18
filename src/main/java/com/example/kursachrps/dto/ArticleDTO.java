package com.example.kursachrps.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {

    private String id;
    private String name;
    private String body;
    private Date dateTime;
    private String link;
    private String fileName;
    private byte[] fileData1;
    private String fileData;
}

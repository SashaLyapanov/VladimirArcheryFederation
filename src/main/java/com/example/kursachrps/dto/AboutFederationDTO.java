package com.example.kursachrps.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AboutFederationDTO {

    private String id;
    private String managers;
    private String contacts;
    private List<String> fileNames;
    private List<String> links;
}

package com.example.kursachrps.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AboutFederationDTO {

    private String id;
    private String managers;
    private String contacts;

    private String linkForRegulation;
    private String fileRegulationName;
    private byte[] fileRegulationData1;
    private String fileRegulationData;

    private String linkForHistory;
    private String fileHistoryName;
    private byte[] fileHistoryData1;
    private String fileHistoryData;
}

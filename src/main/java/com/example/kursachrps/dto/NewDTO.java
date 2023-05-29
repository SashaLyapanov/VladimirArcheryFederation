package com.example.kursachrps.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewDTO {

    private int id;
    private String name;
    private String body;
    private Date date;
    private String picture;

}

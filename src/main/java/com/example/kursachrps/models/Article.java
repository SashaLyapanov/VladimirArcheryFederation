package com.example.kursachrps.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
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

    @Column(name = "link")
    private String link;

    @PrePersist
    private void init() {
        dateTime = new Date();
    }
}

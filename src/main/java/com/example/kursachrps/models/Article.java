package com.example.kursachrps.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Blob;
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
    private Date date;

    @Lob
    @Column(name = "picture1")
    private Blob picture1;

    @Lob
    @Column(name = "picture2")
    private Blob picture2;

    @Lob
    @Column(name = "picture3")
    private Blob picture3;
}

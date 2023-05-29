package com.example.kursachrps.Models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "news")
public class New {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "body")
    private String body;

    @Column(name = "date")
    private Date date;

    @Column(name = "picture")
    private String picture;

}

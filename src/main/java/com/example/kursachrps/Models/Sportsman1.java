package com.example.kursachrps.Models;

import lombok.Data;

@Data
public class Sportsman1 {

    private Long id;
    private String firstName;
    private String lastName;


    public Sportsman1(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

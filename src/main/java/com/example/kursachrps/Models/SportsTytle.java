package com.example.kursachrps.Models;

public enum SportsTytle {

    FIRST_ADULT("1 взрослый"),
    KMS("КМС"),
    MS("МС"),
    MSInternationalClass("МСМК"),
    HonoredMS("ЗМС");

    private String tytle;
    SportsTytle(String s) {
        this.tytle = s;
    }
}

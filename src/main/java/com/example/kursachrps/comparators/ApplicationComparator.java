package com.example.kursachrps.comparators;

import com.example.kursachrps.models.Application;

import java.util.Comparator;

public class ApplicationComparator implements Comparator<Application> {
    @Override
    public int compare(Application app1, Application app2) {
        int bowTypeComparison = app1.getBowType().compareTo(app2.getBowType());
        if (bowTypeComparison != 0) {
            return bowTypeComparison;
        }
        return app1.getSportsman().getSex().compareTo(app2.getSportsman().getSex());
    }
}

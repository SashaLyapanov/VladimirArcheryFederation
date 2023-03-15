package com.example.kursachrps.Models;

import java.util.HashMap;
import java.util.Map;

public class Results {
    private int id;
    private Competition competition;
    private Map<Integer, Integer> resultMap = new HashMap<Integer, Integer>();

    public Results() {

    }

    public Results(Competition competition) {
        this.competition = competition;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Map<Integer, Integer> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<Integer, Integer> resultMap) {
        this.resultMap = resultMap;
    }
}

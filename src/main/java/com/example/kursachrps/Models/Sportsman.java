package com.example.kursachrps.Models;

import jakarta.annotation.Nullable;

import java.util.List;

public class Sportsman extends User {
    private Team team;
    private Coach coach;
    private BowType bowType;
    private SportsTytle spotsTytle;
    private List<Achievment> achievments;
    private Diary diary;
    private List<Competition> competitions;
    private List<Application> applications;



    public Sportsman() {

    }

    public Sportsman(@Nullable Team team, @Nullable Coach coach, BowType bowType, @Nullable SportsTytle spotsTytle) {
        this.team = team;
        this.coach = coach;
        this.bowType = bowType;
        this.spotsTytle = spotsTytle;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public BowType getBowType() {
        return bowType;
    }

    public void setBowType(BowType bowType) {
        this.bowType = bowType;
    }

    public SportsTytle getSpotsTytle() {
        return spotsTytle;
    }

    public void setSpotsTytle(SportsTytle spotsTytle) {
        this.spotsTytle = spotsTytle;
    }

    public List<Achievment> getAchievments() {
        return achievments;
    }

    public void setAchievments(List<Achievment> achievments) {
        this.achievments = achievments;
    }

    public Diary getDiary() {
        return diary;
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
    }

    public List<Competition> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(List<Competition> competitions) {
        this.competitions = competitions;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }







}

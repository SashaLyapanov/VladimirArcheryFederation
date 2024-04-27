package com.example.kursachrps.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "sportsmen")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sportsman extends User {

    @Column(name = "club")
    private String club;

    /**
     * Поле, которое содержит название файла, т.е. картинки, которая хранится в API FileManagerFSLVO.
     */
    @Column(name = "avatar_image")
    private String avatarImage;

    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sex_id", referencedColumnName = "id")
    private Sex sex;

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "sports_title_id", referencedColumnName = "id")
    private SportsTitle sportsTitle;

    @Column(name = "is_regional_team_sportsman")
    private Boolean isRegionalTeamSportsman;

    @JsonIgnore
    @OneToMany(mappedBy = "sportsman")
    private List<Achievement> achievementList;

    @JsonIgnore
    @OneToMany(mappedBy = "sportsman")
    private List<Application> applicationList;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Sportsman_Competition",
            joinColumns = @JoinColumn(name = "sportsman_id"),
            inverseJoinColumns = @JoinColumn(name = "competition_id"))
    private List<Competition> competitionList;

    @OneToMany(mappedBy = "sportsman")
    private List<QualificationRound> qualificationRoundList;

    @OneToMany(mappedBy = "sportsman")
    private List<ProtocolStage2> protocolStage2List;

    @OneToMany(mappedBy = "sportsman")
    private List<ProtocolStage4> protocolStage4List;

    @OneToMany(mappedBy = "sportsman")
    private List<ProtocolStage8> protocolStage8List;

    @OneToMany(mappedBy = "sportsman")
    private List<ProtocolFinal> protocolFinalList;

    public Sportsman() {
    }

    public Sportsman(String email, String password, Role role, Status status, String name, String surname, String patronymic, Date birthDate,
                     SportsTitle sportsTitle, String club, String avatarImage) {
        super(email, password, role, status, name, surname, patronymic, birthDate);
        this.sportsTitle = sportsTitle;
        this.club = club;
        this.avatarImage = avatarImage;
    }

    @Override
    public String toString() {
        return "Sportsman with id: " + getId() + " and surname: " + getSurname();
    }

}

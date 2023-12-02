package com.example.kursachrps.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "session_types")
public class SessionType extends GenericEntity{
//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;

    @Column(name = "session_type_name")
    private String sessionTypeName;

    @OneToMany(mappedBy = "sessionType")
    private List<Session> sessionList;

}

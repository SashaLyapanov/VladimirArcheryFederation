package com.example.kursachrps.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "judges")
public class Judge extends User{
//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @NotEmpty
//    @OneToOne()
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;
}

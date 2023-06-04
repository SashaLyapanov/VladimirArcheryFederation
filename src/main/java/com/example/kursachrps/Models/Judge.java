package com.example.kursachrps.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "judges")
public class Judge extends User{

}

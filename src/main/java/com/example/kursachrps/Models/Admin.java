package com.example.kursachrps.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "admins")
public class Admin extends User{

}

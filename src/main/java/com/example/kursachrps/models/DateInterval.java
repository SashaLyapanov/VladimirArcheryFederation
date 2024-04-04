package com.example.kursachrps.models;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@Embeddable
@EqualsAndHashCode
public class DateInterval {
    private Date startDateTime;
    private Date endDateTime;
}

package com.example.kursachrps.dto.AdditionalDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum StatusOfCompetitionDTO {
    FUTURE,
    PRESENT,
    PAST,
    CANCELLED;

    @JsonValue
    public String getValue() {
        return this.name();
    }

    @JsonCreator
    public static StatusOfCompetitionDTO fromValue(String value) {
        return StatusOfCompetitionDTO.valueOf(value.toUpperCase());
    }
}

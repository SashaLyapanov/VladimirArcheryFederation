package com.example.kursachrps.dto.AdditionalDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QualificationDTO {

    private String id;

    private String qualificationName;

}

package ru.ifmo.se.termwork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RatingDto {

    private StudentResponseDto student;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm dd-MM-yyyy")
    private Date submissionDate;

    private int totalScore;

    private boolean originals;

    private Boolean isOlympiad;

    private int place;

    private int placeOriginal;

    private boolean success;

    private SpecialityDto speciality;
}

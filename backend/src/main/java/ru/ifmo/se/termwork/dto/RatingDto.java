package ru.ifmo.se.termwork.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RatingDto {

    private int totalScore;

    private boolean originals;

    private boolean isOlympiad;

    private int place;

    private int placeOriginal;

    private SpecialityDto speciality;
}

package ru.ifmo.se.termwork.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class RatingsDto {

    @JsonProperty("bvi")
    private Set<RatingDto> olympiad;

    @JsonProperty("ege")
    private Set<RatingDto> exams;
}

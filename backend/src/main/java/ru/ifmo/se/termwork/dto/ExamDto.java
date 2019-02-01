package ru.ifmo.se.termwork.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class ExamDto {

    private String subject;

    @Min(0)
    @Max(100)
    private int score;
}

package ru.ifmo.se.termwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDto {

    @Min(1)
    private int subjectId;

    @Min(0)
    @Max(100)
    private int score;
}

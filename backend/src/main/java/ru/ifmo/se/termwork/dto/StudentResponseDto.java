package ru.ifmo.se.termwork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.ifmo.se.termwork.domain.Achievement;
import ru.ifmo.se.termwork.domain.Exam;

import java.util.Date;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentResponseDto {

    private String surname;

    private String name;

    private String patronymic;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date birthDate;

    private Set<Exam> exams;

    private Set<Achievement> achievements;

    private Set<RatingDto> ratings;
}

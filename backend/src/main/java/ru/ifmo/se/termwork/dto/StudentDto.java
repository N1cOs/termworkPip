package ru.ifmo.se.termwork.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class StudentDto {

    @NotNull
    private String surname;

    @NotNull
    private String name;

    private String patronymic;

    @NotNull
    private Date birthDate;

    private String serialNumber;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    @NotNull
    private String password;

    private List<ExamDto> exams;

    private List<OlympiadDto> olympiads;

    private List<String> achievements;
}

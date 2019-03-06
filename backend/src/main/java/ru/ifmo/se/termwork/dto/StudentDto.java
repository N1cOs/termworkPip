package ru.ifmo.se.termwork.dto;

import lombok.Data;

import javax.validation.constraints.Email;
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

    private Date birthDate;

    @NotNull
    private String serialNumber;

    @Email
    @NotNull
    private String email;

    private String phone;

    @NotNull
    private String password;

    private List<ExamDto> exams;

    private List<Integer> olympiadsId;

    private List<Integer> achievementsId;
}

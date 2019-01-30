package ru.ifmo.se.termwork.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class StudentDTO {

    @NotNull
    private String surname;

    private String name;

    private String patronymic;

    private Date birthDate;

    private String serialNumber;

    private String email;

    private String phone;

    private String password;

    private List<ExamDTO> exams;

    private List<AchievementDTO> achievements;

    private List<OlympiadDTO> olympiads;

}

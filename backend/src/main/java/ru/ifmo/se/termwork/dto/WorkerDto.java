package ru.ifmo.se.termwork.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WorkerDto {

    private String email;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    private String patronymic;

    @NotNull
    private String password;
}

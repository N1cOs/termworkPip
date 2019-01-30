package ru.ifmo.se.termwork.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDTO {
    @NotNull
    private String username;
    @NotNull
    private String password;
}

package ru.ifmo.se.termwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInDto {

    private int userId;

    private String token;
}

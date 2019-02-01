
package ru.ifmo.se.termwork.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CredentialDto {

    @NotNull
    private String password;

    @NotNull
    private String newValue;
}
package ru.ifmo.se.termwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class ExceptionDto {

    @NonNull
    private String info;
}

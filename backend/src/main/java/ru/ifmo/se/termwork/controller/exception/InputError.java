package ru.ifmo.se.termwork.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InputError {

    private String field;

    private String info;
}

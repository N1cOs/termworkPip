package ru.ifmo.se.termwork.support.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InputError {

    private String field;

    private String info;
}

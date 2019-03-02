package ru.ifmo.se.termwork.support.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class InputError {

    private final String field;

    private String info;
}

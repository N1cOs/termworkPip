package ru.ifmo.se.termwork.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
public class Error {

    private String info;

    private List<InputError> inputErrors;
}

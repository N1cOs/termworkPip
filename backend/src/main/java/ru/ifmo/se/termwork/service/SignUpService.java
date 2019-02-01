package ru.ifmo.se.termwork.service;

import ru.ifmo.se.termwork.dto.StudentDto;

public interface SignUpService {

    boolean isExists(String serialNumber);

    void signUp(StudentDto studentDTO);
}

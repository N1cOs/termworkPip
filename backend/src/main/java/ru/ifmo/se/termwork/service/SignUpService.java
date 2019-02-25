package ru.ifmo.se.termwork.service;

import ru.ifmo.se.termwork.dto.StudentDto;

public interface SignUpService {

    void signUp(StudentDto studentDTO);

    boolean isEmailExists(String email);

}

package ru.ifmo.se.termwork.service;

import ru.ifmo.se.termwork.dto.StudentDTO;

public interface SignUpService {

    boolean checkExistence(String serialNumber);

    boolean signUp(StudentDTO studentDTO);


}

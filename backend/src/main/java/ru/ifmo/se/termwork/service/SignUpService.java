package ru.ifmo.se.termwork.service;

import ru.ifmo.se.termwork.dto.StudentDto;
import ru.ifmo.se.termwork.dto.WorkerDto;

public interface SignUpService {

    void signUp(StudentDto studentDTO);

    void signUp(WorkerDto workerDto, String link);

    void addWorkerSignUp(int headWorkerId, String workerEmail);
}

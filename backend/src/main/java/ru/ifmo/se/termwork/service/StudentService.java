package ru.ifmo.se.termwork.service;

import ru.ifmo.se.termwork.dto.StudentResponseDto;

public interface StudentService {

    StudentResponseDto getStudentDto(int studentId);
}

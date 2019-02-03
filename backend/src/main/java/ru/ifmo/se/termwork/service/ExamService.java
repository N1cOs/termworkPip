package ru.ifmo.se.termwork.service;

import ru.ifmo.se.termwork.dto.ExamDto;

import java.util.List;

public interface ExamService {

    void saveExams(int studentId, List<ExamDto> exams);

    void deleteExams(int studentId, List<ExamDto> exams);
}

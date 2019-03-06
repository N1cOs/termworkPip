package ru.ifmo.se.termwork.service;

import ru.ifmo.se.termwork.domain.Rating;
import ru.ifmo.se.termwork.domain.Student;

public interface ComputeService {

    void computeScoreAndSaveAsync(Student student, Rating rating);

    Student recomputeExams(int studentId);

    Student recomputeAchievements(int studentId);

    Student recomputeAll(int studentId);
}

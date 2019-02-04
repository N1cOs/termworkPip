package ru.ifmo.se.termwork.service.mappers;

import ru.ifmo.se.termwork.domain.Exam;
import ru.ifmo.se.termwork.dto.ExamDto;
import ru.ifmo.se.termwork.dto.StudentDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExamMapper {
    public Set<Exam> map(List<ExamDto> listDto){
        return new HashSet<>();
    }
}

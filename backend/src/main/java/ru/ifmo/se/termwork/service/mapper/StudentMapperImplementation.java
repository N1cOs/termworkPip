package ru.ifmo.se.termwork.service.mapper;

import ru.ifmo.se.termwork.domain.Student;
import ru.ifmo.se.termwork.dto.StudentDTO;

public class StudentMapperImplementation extends StudentMapper {
    @Override
    public Student studentDtoToStudent(StudentDTO studentDTO) {
        return new Student();
    }
}

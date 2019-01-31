package ru.ifmo.se.termwork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.domain.Student;
import ru.ifmo.se.termwork.dto.StudentDTO;
import ru.ifmo.se.termwork.repository.StudentRepository;
import ru.ifmo.se.termwork.service.SignUpService;
import ru.ifmo.se.termwork.service.mapper.StudentMapper;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    StudentRepository studentRepository;

    @Override
    public boolean signUp(StudentDTO studentDTO) {
        Student student = studentMapper.studentDtoToStudent(studentDTO);

        studentRepository.save(student);
        return true;
    }

    @Override
    public boolean checkExistence(String serialNumber) {
        Student student = studentRepository.findBySerialNumber(serialNumber);
        if (student != null)
            return true;
        else
            return false;

    }

}

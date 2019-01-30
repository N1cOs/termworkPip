package ru.ifmo.se.termwork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.domain.Student;
import ru.ifmo.se.termwork.dto.StudentDTO;
import ru.ifmo.se.termwork.repository.StudentRepository;
import ru.ifmo.se.termwork.service.SignUpService;

@Service
public class SignUpServiceImpl implements SignUpService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public boolean signUp(StudentDTO studentDTO) {
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

    public static boolean isNullOrEmpty(String str) {
        if (str != null && !str.isEmpty())
            return false;
        return true;
    }
}

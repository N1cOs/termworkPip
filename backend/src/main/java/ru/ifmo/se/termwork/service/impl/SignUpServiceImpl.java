package ru.ifmo.se.termwork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.dto.StudentDto;
import ru.ifmo.se.termwork.repository.StudentRepository;
import ru.ifmo.se.termwork.service.SignUpService;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public boolean isExists(String serialNumber) {
        return studentRepository.findBySerialNumber(serialNumber).isPresent();
    }

    @Override
    public void signUp(StudentDto studentDTO) {

    }
}

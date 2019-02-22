package ru.ifmo.se.termwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.controller.exception.InputError;
import ru.ifmo.se.termwork.domain.Student;
import ru.ifmo.se.termwork.dto.StudentDto;
import ru.ifmo.se.termwork.repository.AchievementRepository;
import ru.ifmo.se.termwork.repository.OlympiadRepository;
import ru.ifmo.se.termwork.repository.StudentRepository;
import ru.ifmo.se.termwork.repository.SubjectRepository;
import ru.ifmo.se.termwork.service.JabberService;
import ru.ifmo.se.termwork.service.SignUpService;
import ru.ifmo.se.termwork.service.mappers.StudentMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final static Map<Integer, InputError> errors;

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    private final SubjectRepository subjectRepository;

    private final OlympiadRepository olympiadRepository;

    private final AchievementRepository achievementRepository;

    private final PasswordEncoder passwordEncoder;

    private final JabberService jabberService;

    static {
        Map<Integer, InputError> inputErrors = new HashMap<>();
        inputErrors.put(StudentRepository.SqlError.EMAIL,
                new InputError("email", "exception.signUp.email"));

        //ToDo: add serial number and phone

        errors = Collections.unmodifiableMap(inputErrors);
    }

    @Override
    public void signUp(StudentDto studentDto) {
        Student student = studentMapper.
                toStudent(studentDto, subjectRepository, achievementRepository, olympiadRepository);
        String encodedPassword = passwordEncoder.encode(student.getPassword());
        student.setPassword(encodedPassword);

        //ToDo: exception handling
        studentRepository.save(student);
        saveToJabber(studentDto.getEmail(), studentDto.getPassword());
    }

    private void saveToJabber(String email, String password){
        String username = email.substring(0, email.lastIndexOf('@'));
        Map<String, String> attributes = new HashMap<>();
        attributes.put("email", email);
        attributes.put("name", username);
        jabberService.signUp(username, password, attributes);
    }
}

package ru.ifmo.se.termwork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.postgresql.util.PSQLException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.controller.exception.ApiException;
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

import java.util.*;

@Log4j
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
        inputErrors.put(StudentRepository.SqlError.EMAIl_PHONE,
                new InputError("email, phone", "exception.signUp.email_phone"));
        inputErrors.put(StudentRepository.SqlError.EMAIL_SERIAL_NUMBER,
                new InputError("email, serial number", "exception.signUp.email_serialNumber"));
        inputErrors.put(StudentRepository.SqlError.SERIAL_NUMBER,
                new InputError("serial number", "exception.signUp.serialNumber"));
        inputErrors.put(StudentRepository.SqlError.PHONE,
                new InputError("phone", "exception.signUp.phone"));
        inputErrors.put(StudentRepository.SqlError.SERIAL_NUMBER_PHONE,
                new InputError("phone, serial number", "exception.signUp.phone_serialNumber"));
        inputErrors.put(StudentRepository.SqlError.ALL,
                new InputError("email, phone and serial number", "exception.signUp.all"));

        errors = Collections.unmodifiableMap(inputErrors);
    }

    void chooseState(String sqlState) throws ApiException{
        switch (sqlState){
            case "23030":
                throw new ApiException(HttpStatus.FORBIDDEN, errors.get(StudentRepository.SqlError.EMAIL).getInfo());
            case "23031":
                throw new ApiException(HttpStatus.FORBIDDEN, errors.get(StudentRepository.SqlError.SERIAL_NUMBER).getInfo());
            case "23032":
                throw new ApiException(HttpStatus.FORBIDDEN, errors.get(StudentRepository.SqlError.PHONE).getInfo());
            case "23033":
                throw new ApiException(HttpStatus.FORBIDDEN, errors.get(StudentRepository.SqlError.EMAIL).getInfo());
            case "23034":
                throw new ApiException(HttpStatus.FORBIDDEN, errors.get(StudentRepository.SqlError.EMAIL_SERIAL_NUMBER).getInfo());
            case "23035":
                throw new ApiException(HttpStatus.FORBIDDEN, errors.get(StudentRepository.SqlError.SERIAL_NUMBER_PHONE).getInfo());
            case "23036":
                throw new ApiException(HttpStatus.FORBIDDEN, errors.get(StudentRepository.SqlError.ALL).getInfo());
        }
    }

    @Override
    public void signUp(StudentDto studentDto) {
        Student student = studentMapper.
                toStudent(studentDto, subjectRepository, achievementRepository, olympiadRepository);
        String encodedPassword = passwordEncoder.encode(student.getPassword());
        student.setPassword(encodedPassword);

        //ToDo: exception handling
        try {
            studentRepository.save(student);
        } catch (Exception ex) {
            PSQLException psqlException = (PSQLException) NestedExceptionUtils.getMostSpecificCause(ex);
            chooseState(psqlException.getSQLState());
        }

        saveToJabber(studentDto.getEmail(), studentDto.getPassword());
    }

    private void saveToJabber(String email, String password) {
        String username = email.substring(0, email.lastIndexOf('@'));
        Map<String, String> attributes = new HashMap<>();
        attributes.put("email", email);
        attributes.put("name", username);
        jabberService.signUp(username, password, attributes);
    }


}

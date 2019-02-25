package ru.ifmo.se.termwork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.postgresql.util.PSQLException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
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
        inputErrors.put(StudentRepository.SqlError.SERIAL_NUMBER,
                new InputError("serial number", "exception.signUp.serialNumber"));
        inputErrors.put(StudentRepository.SqlError.PHONE,
                new InputError("phone", "exception.signUp.phone"));
        errors = Collections.unmodifiableMap(inputErrors);
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
        } catch (DataIntegrityViolationException ex) {
            PSQLException psqlException = (PSQLException) NestedExceptionUtils.getMostSpecificCause(ex);
            throwApiExceptionUponState(psqlException.getSQLState());
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


    private void throwApiExceptionUponState(String sqlState) throws ApiException{
        switch (Integer.parseInt(sqlState)) {
            case StudentRepository.SqlError.EMAIL:
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        Collections.singletonList(errors.get(StudentRepository.SqlError.EMAIL)));
            case StudentRepository.SqlError.SERIAL_NUMBER:
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        Collections.singletonList(errors.get(StudentRepository.SqlError.SERIAL_NUMBER)));
            case StudentRepository.SqlError.PHONE:
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        Collections.singletonList(errors.get(StudentRepository.SqlError.PHONE)));
            case StudentRepository.SqlError.EMAIl_PHONE:
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        Arrays.asList(errors.get(StudentRepository.SqlError.EMAIL),
                                errors.get(StudentRepository.SqlError.PHONE)));
            case StudentRepository.SqlError.EMAIL_SERIAL_NUMBER:
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        Arrays.asList(errors.get(StudentRepository.SqlError.EMAIL),
                                errors.get(StudentRepository.SqlError.SERIAL_NUMBER)));
            case StudentRepository.SqlError.SERIAL_NUMBER_PHONE:
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        Arrays.asList(errors.get(StudentRepository.SqlError.SERIAL_NUMBER),
                                errors.get(StudentRepository.SqlError.PHONE)));
            case StudentRepository.SqlError.ALL:
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        Arrays.asList(errors.get(StudentRepository.SqlError.EMAIL),
                                errors.get(StudentRepository.SqlError.SERIAL_NUMBER),
                                errors.get(StudentRepository.SqlError.PHONE)));
        }
    }
}

package ru.ifmo.se.termwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ifmo.se.termwork.controller.exception.ApiException;
import ru.ifmo.se.termwork.controller.exception.InputError;
import ru.ifmo.se.termwork.domain.Student;
import ru.ifmo.se.termwork.dto.StudentDto;
import ru.ifmo.se.termwork.repository.*;
import ru.ifmo.se.termwork.service.JabberService;
import ru.ifmo.se.termwork.service.SignUpService;
import ru.ifmo.se.termwork.service.mappers.StudentMapper;

import java.sql.SQLException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final static Map<Integer, InputError> ERRORS;

    private final UserRepository userRepository;

    private final StudentRepository studentRepository;

    private final SubjectRepository subjectRepository;

    private final OlympiadRepository olympiadRepository;

    private final AchievementRepository achievementRepository;

    private final StudentMapper studentMapper;

    private final PasswordEncoder passwordEncoder;

    private final JabberService jabberService;

    static {
        Map<Integer, InputError> inputErrors = new HashMap<>();
        inputErrors.put(StudentRepository.SqlError.EMAIL,
                new InputError("email", "exception.signUp.email"));
        inputErrors.put(StudentRepository.SqlError.SERIAL_NUMBER,
                new InputError("serial_number", "exception.signUp.serialNumber"));
        inputErrors.put(StudentRepository.SqlError.PHONE,
                new InputError("phone", "exception.signUp.phone"));
        ERRORS = Collections.unmodifiableMap(inputErrors);
    }

    @Override
    public boolean isEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public void signUp(StudentDto studentDto) {
        Student student = studentMapper.
                toStudent(studentDto, subjectRepository, achievementRepository, olympiadRepository);
        String encodedPassword = passwordEncoder.encode(student.getPassword());
        student.setPassword(encodedPassword);

        try {
            studentRepository.save(student);
        } catch (DataIntegrityViolationException ex) {
            Throwable rootCause = ex.getRootCause();
            if(rootCause instanceof SQLException){
                SQLException sqlException = (SQLException) rootCause;
                throwExceptionUponState(sqlException.getSQLState());
            }
        }

        saveToJabber(studentDto.getEmail(), studentDto.getPassword());
    }

    private void throwExceptionUponState(String sqlState) throws ApiException{
        switch (Integer.parseInt(sqlState)) {
            case StudentRepository.SqlError.EMAIL:
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        Collections.singletonList(ERRORS.get(StudentRepository.SqlError.EMAIL)));
            case StudentRepository.SqlError.SERIAL_NUMBER:
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        Collections.singletonList(ERRORS.get(StudentRepository.SqlError.SERIAL_NUMBER)));
            case StudentRepository.SqlError.PHONE:
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        Collections.singletonList(ERRORS.get(StudentRepository.SqlError.PHONE)));
            case StudentRepository.SqlError.EMAIl_PHONE:
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        Arrays.asList(ERRORS.get(StudentRepository.SqlError.EMAIL),
                                ERRORS.get(StudentRepository.SqlError.PHONE)));
            case StudentRepository.SqlError.EMAIL_SERIAL_NUMBER:
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        Arrays.asList(ERRORS.get(StudentRepository.SqlError.EMAIL),
                                ERRORS.get(StudentRepository.SqlError.SERIAL_NUMBER)));
            case StudentRepository.SqlError.SERIAL_NUMBER_PHONE:
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        Arrays.asList(ERRORS.get(StudentRepository.SqlError.SERIAL_NUMBER),
                                ERRORS.get(StudentRepository.SqlError.PHONE)));
            case StudentRepository.SqlError.ALL:
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        new ArrayList<>(ERRORS.values()));
        }
    }

    private void saveToJabber(String email, String password) {
        String username = email.substring(0, email.lastIndexOf('@'));
        Map<String, String> attributes = new HashMap<>();
        attributes.put("email", email);
        attributes.put("name", username);
        jabberService.signUp(username, password, attributes);
    }
}
